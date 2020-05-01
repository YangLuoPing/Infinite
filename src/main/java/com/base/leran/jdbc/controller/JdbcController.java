package com.base.leran.jdbc.controller;

import com.base.leran.jdbc.servcie.JdbcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jdbc")
@Api(value = "JDBC多数据源测试",tags = {"Jdbc用户擦操作接口"})
public class JdbcController {

    @Autowired
    JdbcService jdbcService;

    //查询employee表中所有数据
    //List 中的1个 Map 对应数据库的 1行数据
    //Map 中的 key 对应数据库的字段名，value 对应数据库的字段值
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation("人员查询接口")
    public List<Map<String, Object>> userList() {
        return jdbcService.findAll();
    }

    @GetMapping("/add")
    @ApiOperation("新增一个用户")
    public String addUser() {
        return jdbcService.addUser();
    }

    @GetMapping("/update/{code}")
    @ApiOperation("修改用户信息")
    public String updateUser(@ApiParam("这个code会被返回") @PathVariable("code") String code) {
        return jdbcService.updateUser(code);
    }


    @GetMapping("/delete/{code}")
    @ApiOperation("删除用户")
    public String delUser(@PathVariable("code") int code) {

        return jdbcService.delUser(code);
    }
}
