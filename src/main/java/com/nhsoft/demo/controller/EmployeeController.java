package com.nhsoft.demo.controller;

import com.nhsoft.demo.Dto.Response;
import com.nhsoft.demo.entity.Employee;
import com.nhsoft.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
/*@RequestMapping(value = "employee")*/
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //获取特定员工
    @GetMapping(value = "/getOne")
    public Response get(@RequestParam("systemBookCode") String systemBookCode,
                        @RequestParam("branchNum") Integer branchNum,
                        @RequestParam("employeeNum") Integer employeeNum) throws Exception {
        Employee employee = employeeService.getEmployee(systemBookCode, branchNum, employeeNum);
        return Response.response(200, employee, "员工查询成功");
    }

    //获取所有或含关键词员工员工
    @GetMapping("/getEmployee")
    public Response getAll(@RequestParam("systemBookCode") String systemBookCode,
                           @RequestParam(value = "keyWord", required = false) String keyWord) throws Exception {
        if (keyWord != null) {
            List<Employee> searchEmployee = employeeService.searchEmployee(systemBookCode, keyWord, keyWord);
            if (searchEmployee == null) {
                throw new Exception("未找到与关键词相关联员工");
            }
            return Response.response(200, searchEmployee, "员工查询成功");
        }
        List<Employee> allEmployee = employeeService.findAllEmployee(systemBookCode);
        if (allEmployee == null) {
            throw new Exception("未找到任何员工");
        }
        return Response.response(200, allEmployee, "查询成功");
    }

    //添加员工
    @PostMapping("/add")
    public Response add(@RequestBody Employee employee) throws Exception {
        Employee result = employeeService.addEmployee(employee);
        if (result == null) {
            throw new Exception("员工添加失败,该员工信息已存在");
        }
        return Response.response(200, result, "员工添加成功");
    }

    //删除员工
    @DeleteMapping("/delete")
    public Response deleteEmployee(@RequestBody List<Map> deleteList) throws Exception {
        List<Employee> result = employeeService.deleteEmployee(deleteList);
        return Response.response(200, result, "员工删除成功");
    }

    //获取最新员工
    @GetMapping("/getLastEmployee")
    public Response<Employee> getLastEmployee(@RequestParam("systemBookCode") String systemBookCode) {
        Employee lastEmployee = employeeService.getLastEmployee(systemBookCode);
        return Response.response(200, lastEmployee, "查询成功");
    }

    //更改Grid列表的员工信息
    @PostMapping("/updateEmployee")
    public Response updateEmployee(@RequestBody Employee employee) {
        Employee NewEmployee = employeeService.updateEmployee(employee);
        return Response.response(200, NewEmployee, "更新成功");
    }

    //pageNum,pageSize,searchText,branchNum

}
