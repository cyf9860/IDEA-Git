package com.nhsoft.demo.service.Impl;

import com.nhsoft.demo.entity.EmployeeType;
import com.nhsoft.demo.Dao.EmployeeTypeRepository;
import com.nhsoft.demo.service.EmployeeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeTypeServiceImpl implements EmployeeTypeService {
    @Autowired
    EmployeeTypeRepository employeeTypeRepository;

    @Autowired
    RedisTemplate<String, Object> MyRedisTemplate;

    //查找所有员工类型
    @Cacheable(value = "employeeType",key = "#a0")
    public List<EmployeeType> findAllType(String systemBookCode) {
        List<EmployeeType> employeeTypes = employeeTypeRepository.findAllBySystemBookCode(systemBookCode);
        return employeeTypes;
    }


    //查找特定员工类型
    public List<EmployeeType> findAllType(String systemBookCode,Integer employeeTypeNum) {
        List<EmployeeType> employeeTypes = employeeTypeRepository.findAllBySystemBookCodeAndEmployeeTypeNum(systemBookCode,employeeTypeNum);
        return employeeTypes;
    }

    //添加员工类型
    public EmployeeType addEmployeeType(EmployeeType employeeType) {
        if (employeeTypeRepository.readBySystemBookCodeAndEmployeeTypeNum(employeeType.getSystemBookCode(),
                employeeType.getEmployeeTypeNum())!=null) {
            return null;
        }
        String systemBookCode = employeeType.getSystemBookCode();
        if (getLastEmployeeType(systemBookCode) == null) {
            employeeType.setEmployeeTypeNum((int) MyRedisTemplate.opsForValue().increment("employeeTypeNum", 1).longValue());
        } else if (getLastEmployeeType(systemBookCode) != null&&MyRedisTemplate.opsForValue().get("employeeTypeNum")==null) {
            int id = getLastEmployeeType(systemBookCode).getEmployeeTypeNum();
            employeeType.setEmployeeTypeNum((int) MyRedisTemplate.opsForValue().increment("employeeTypeNum", id+1).longValue());
        } else {
            employeeType.setEmployeeTypeNum((int) MyRedisTemplate.opsForValue().increment("employeeTypeNum", 1).longValue());
        }
        return employeeTypeRepository.save(employeeType);
    }

    //更新员工类型
    public EmployeeType updateEmployeeType(EmployeeType employeeType) throws Exception {
        return employeeTypeRepository.save(employeeType);
    }

    //删除员工类型
    @CachePut(value = "employeeType",key="#a0.getSystemBookCode()")
    public List<EmployeeType> deleteEmployeeType(EmployeeType employeeType) {
        if (employeeType.getEmployeeTypeNum() != null) {
            employeeTypeRepository.deleteBySystemBookCodeAndEmployeeTypeNum(employeeType.getSystemBookCode(), employeeType.getEmployeeTypeNum());
        }
        employeeTypeRepository.deleteBySystemBookCodeAndEmployeeTypeName(employeeType.getSystemBookCode(),employeeType.getEmployeeTypeName());

        List<EmployeeType> allType = findAllType(employeeType.getSystemBookCode());
        return allType;
    }


    //查询最新主键
    public EmployeeType getLastEmployeeType(String systemBookCode) {
        EmployeeType employeeType = employeeTypeRepository.readTop1BySystemBookCodeOrderByEmployeeTypeNumDesc(systemBookCode);
        if (employeeType == null) {
            return null;
        }
        return employeeType;
    }
}
