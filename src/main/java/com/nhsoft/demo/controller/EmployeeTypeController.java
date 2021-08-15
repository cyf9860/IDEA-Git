package com.nhsoft.demo.controller;

import com.nhsoft.demo.Dto.Response;
import com.nhsoft.demo.entity.EmployeeType;
import com.nhsoft.demo.service.EmployeeTypeService;
import com.nhsoft.demo.service.Impl.EmployeeTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeTypeController {
    @Autowired
    EmployeeTypeService employeeTypeService;

    @GetMapping("/findAllType")
    public Response findAllType(@RequestParam("systemBookCode") String systemBookCode, @RequestParam(value = "employeeTypeNum",required = false) Integer employeeTypeNum) throws Exception {
        if (employeeTypeNum != null) {
            List<EmployeeType> allType = employeeTypeService.findAllType(systemBookCode, employeeTypeNum);
            return Response.response(200, allType, "查询员工类型成功");
        }
        List<EmployeeType> allType = employeeTypeService.findAllType(systemBookCode);
        if (allType == null) {
            throw new Exception("查询员工类型失败");
        }
        return Response.response(200, allType, "查询员工类型成功");
    }

    @PostMapping("/addType")
    public Response addEmployeeType(@RequestBody EmployeeType employeeType) throws Exception {
        EmployeeType addEmployeeType = employeeTypeService.addEmployeeType(employeeType);
        if (addEmployeeType == null) {
            throw new Exception("添加员工类型失败,员工类型已存在");
        }
        return Response.response(200, addEmployeeType, "添加员工类型成功");
    }

    @PostMapping("/updateType")
    public Response updateEmployeeType(@RequestBody EmployeeType employeeType) throws Exception {
        EmployeeType result = employeeTypeService.updateEmployeeType(employeeType);
        if (result == null) {
            throw new Exception("员工类型更新失败");
        }
        return Response.response(200, result, "员工更新类型成功");
    }

    @DeleteMapping("/deleteType")
    public Response deleteEmployeeType(@RequestBody EmployeeType employeeType) throws Exception {
        List<EmployeeType> result = employeeTypeService.deleteEmployeeType(employeeType);
        return Response.response(200, result, "员工类型删除成功");
    }

    @GetMapping("/getLastEmployeeTypeNum")
    public Response<EmployeeType> getLastEmployeeType(@RequestParam("systemBookCode") String systemBookCode) {
        EmployeeType lastEmployeeType = employeeTypeService.getLastEmployeeType(systemBookCode);
        return Response.response(200, lastEmployeeType, "查询成功");
    }
}
