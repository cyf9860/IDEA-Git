package com.nhsoft.demo.service.Impl;

import com.nhsoft.demo.Dto.Response;
import com.nhsoft.demo.entity.Employee;
import com.nhsoft.demo.Dao.EmployeeRepository;
import com.nhsoft.demo.entity.EmployeeType;
import com.nhsoft.demo.entity.Employee_;
import com.nhsoft.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.nhsoft.demo.entity.Employee_.SYSTEM_BOOK_CODE;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    RedisTemplate<String, Object> MyRedisTemplate;

    //查询特定员工
    public Employee getEmployee(String systemBookCode, Integer branchNum, Integer employeeNum) throws Exception {
        Employee employee = employeeRepository.readBySystemBookCodeAndBranchNumAndEmployeeNum(systemBookCode, branchNum, employeeNum);
        if (employee == null) {
            throw new Exception("查找的员工不存在");
        }
        return employee;
    }

    //查询所有员工
    @Cacheable(value = "employee",key = "#a0")
    public List<Employee> findAllEmployee(String systemBookCode) {
//        Sort employeeNumSort = Sort.by(Sort.Direction.ASC, "employeeNum");
        List<Employee> employees = employeeRepository.findAllBySystemBookCode(systemBookCode);
        return employees;
    }


    //添加员工
    @CachePut(value = "employee",key = "#lastEmployee")
    public Employee addEmployee(Employee employee) {
        if (employeeRepository.readBySystemBookCodeAndBranchNumAndEmployeeNum(employee.getSystemBookCode(),
                employee.getBranchNum(), employee.getEmployeeNum()) != null) {
            return null;
        }
        if (employeeRepository.readByEmployeeCodeAndEmployeeName(employee.getEmployeeCode(), employee.getEmployeeName()) != null) {
            return null;
        }
        String systemBookCodeAdd = employee.getSystemBookCode();
        if (getLastEmployee(systemBookCodeAdd) == null) {
            employee.setEmployeeNum((int) MyRedisTemplate.opsForValue().increment("employeeNum", 1).longValue());
        } else if (getLastEmployee(systemBookCodeAdd) != null && MyRedisTemplate.opsForValue().get("employeeNum") == null) {
            int id = getLastEmployee(systemBookCodeAdd).getEmployeeNum();
            employee.setEmployeeNum((int) MyRedisTemplate.opsForValue().increment("employeeNum", id + 1).longValue());
        } else {
            employee.setEmployeeNum((int) MyRedisTemplate.opsForValue().increment("employeeNum", 1).longValue());
        }
        return employeeRepository.save(employee);
    }

    //删除员工
    @CachePut(value = "employee",key = "#a0.get(0).get('systemBookCode')")
    public List<Employee> deleteEmployee(List<Map> deleteList) throws Exception {
        for (Map employee : deleteList) {
            if (employeeRepository.readBySystemBookCodeAndBranchNumAndEmployeeNum(
                    (String) employee.get("systemBookCode"), (Integer) employee.get("branchNum"), (Integer) employee.get("employeeNum")) == null) {
                throw new Exception("要删除的用户不存在");
            }
            employeeRepository.deleteBySystemBookCodeAndBranchNumAndEmployeeNum((String) employee.get("systemBookCode"), (Integer) employee.get("branchNum"), (Integer) employee.get("employeeNum"));
        }
        List<Employee> employees = employeeRepository.findAllBySystemBookCode((String) deleteList.get(0).get("systemBookCode"));

        return employees;
    }

    //获取最新员工
    @Cacheable(value = "employee",key = "#lastEmployee")
    public Employee getLastEmployee(String systemBookCode) {
        return employeeRepository.readTop1BySystemBookCodeOrderByEmployeeNumDesc(systemBookCode);
    }

    //根据员工编码关键词模糊查询
    public List<Employee> searchEmployee(String systemBookCode, String employeeCode, String employeeName) {
        List<Employee> employeeList = employeeRepository.findAllByEmployeeCodeOrEmployeeNameLike(systemBookCode, employeeCode, employeeName);
        if (employeeList == null) {
            return null;
        }
        return employeeList;
    }

    //更新员工
    @CachePut(value = "employee",key = "#a0.getSystemBookCode()+':'+#a0.getBranchNum()+':'+#a0.getEmployeeNum()")
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

//    public Page<Employee> getAll(){
//        Specification specification = new Specification() {
//            @Override
//            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                criteriaBuilder.like()
//            }
//        }
//        employeeRepository.findAll(specification, PageRequest.of(pageNum,pageSize,Sort.by(Sort.Direction.DESC,"")))
//    }
}
