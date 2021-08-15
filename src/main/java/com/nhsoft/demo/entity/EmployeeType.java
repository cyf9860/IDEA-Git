package com.nhsoft.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@IdClass(EmployeeType.EmployeeTypeKey.class)
@Table(name = "employee_type_demo")
public class EmployeeType implements Serializable{
    private static final long serialVersionUID = -5809782578272943988L;
    @Id
    private String systemBookCode;
    @Id
    private Integer employeeTypeNum;
    private String employeeTypeCode;
    private String employeeTypeName;
    private Integer employeeTypeParentNum;

    @Data
    @EqualsAndHashCode
    public static class EmployeeTypeKey implements Serializable {
        private String systemBookCode;
        private Integer employeeTypeNum;
    }
}
