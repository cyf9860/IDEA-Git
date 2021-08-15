package com.nhsoft.demo.service;

import com.nhsoft.demo.Dto.Response;
import com.nhsoft.demo.entity.Employee;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    //查询特定员工
    public Employee getEmployee(String systemBookCode, Integer branchNum, Integer employeeNum) throws Exception;

    //查询所有员工
    public List<Employee> findAllEmployee(String systemBookCode);

    //添加员工
    public Employee addEmployee(Employee employee);


    //删除员工
    public List<Employee> deleteEmployee(List<Map> deleteList) throws Exception;

    //获取最新员工
    public Employee getLastEmployee(String systemBookCode);


    //根据员工编码关键词模糊查询
    public List<Employee> searchEmployee(String systemBookCode, String employeeCode, String employeeName);

    //分页查询
//    public Response readAll(String keyWord, Integer pageNum, Integer pageSize);

    //更新员工
    public Employee updateEmployee(Employee employee);
}
