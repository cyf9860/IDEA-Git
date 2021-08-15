package com.nhsoft.demo.Dao;

import com.nhsoft.demo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Employee.EmployeeKey> {
    Employee readBySystemBookCodeAndBranchNumAndEmployeeNum(String systemBookCode, Integer branchNum, Integer employeeNum);

    Integer deleteBySystemBookCodeAndBranchNumAndEmployeeNum(String systemBookCode, Integer branchNum, Integer employeeNum);

    Employee readTop1BySystemBookCodeOrderByEmployeeNumDesc(String systemBookCode);

    List<Employee> findAllBySystemBookCode(String systemBookCode);

    @Query(nativeQuery = true, value = "select * from employee_demo where system_book_code = ?1 and (employee_code like %?2% or employee_name like %?3%)")
    List<Employee> findAllByEmployeeCodeOrEmployeeNameLike(String systemBookCode,String employeeCode,String employeeName);

    Employee readByEmployeeCodeAndEmployeeName(String employeeCode, String employeeName);
}
