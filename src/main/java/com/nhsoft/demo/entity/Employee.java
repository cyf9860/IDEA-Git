package com.nhsoft.demo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@IdClass(Employee.EmployeeKey.class)
@Table(name = "employee_demo")
public class Employee implements Serializable{
    private static final long serialVersionUID = -5809782578272943999L;

    @Id
    private String systemBookCode;
    @Id
    private Integer branchNum;
    @Id
    private Integer employeeNum;
    private String employeeCode;
    private String employeeName;
    private Integer employeeTypeNum;
    private String employeeIdCode;
    private String employeeLinktel;
    private Integer employeeActived;
    private String employeeMemo;
    private Date employeeHireDate;



    @Data
    @EqualsAndHashCode
   public static class EmployeeKey implements Serializable {
        private static final long serialVersionUID = -58097825782729439L;
        private String systemBookCode;
        private Integer branchNum;
        private Integer employeeNum;
    }
}

