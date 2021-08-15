package com.nhsoft.demo.Dao;

import com.nhsoft.demo.entity.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, Integer> {

    @Query(nativeQuery = true, value = "select * from employee_type_demo order by employee_type_num")
    List<EmployeeType> findAllEmployeeType();

    EmployeeType readBySystemBookCodeAndEmployeeTypeNum(String systemBookCode, Integer employeeTypeNum);

    Integer deleteBySystemBookCodeAndEmployeeTypeName(String systemBookCode, String EmployeeTypeName);

    Integer deleteBySystemBookCodeAndEmployeeTypeNum(String systemBookCode, Integer employeeTypeNum);

    EmployeeType readTop1BySystemBookCodeOrderByEmployeeTypeNumDesc(String systemBookCode);

    List<EmployeeType> findAllBySystemBookCode(String systemBookCode);

    List<EmployeeType> findAllBySystemBookCodeAndEmployeeTypeNum(String systemBookCode,Integer employeeTypeNum);

}

