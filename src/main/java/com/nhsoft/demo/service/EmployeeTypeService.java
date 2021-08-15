package com.nhsoft.demo.service;

import com.nhsoft.demo.entity.EmployeeType;

import java.util.List;

public interface EmployeeTypeService {
    //查找所有员工类型
    public List<EmployeeType> findAllType(String systemBookCode);

    //查找特定员工类型
    public List<EmployeeType> findAllType(String systemBookCode, Integer employeeTypeNum);

    //添加所有员工类型
    public EmployeeType addEmployeeType(EmployeeType employeeType);

    //更新员工类型
    public EmployeeType updateEmployeeType(EmployeeType employeeType) throws Exception;

    //通过联合主键删除员工类型
    public List<EmployeeType> deleteEmployeeType(EmployeeType employeeType);

    //查询最新主键
    public EmployeeType getLastEmployeeType(String systemBookCode);

}
