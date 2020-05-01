package com.base.leran.jdbc;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jdbc")
public class JdbcController {
    /**
     * Spring Boot 默认提供了数据源，默认提供了 org.springframework.jdbc.core.JdbcTemplate
     * JdbcTemplate 中会自己注入数据源，用于简化 JDBC操作
     * 还能避免一些常见的错误,使用起来也不用再自己来关闭数据库连接
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    //查询employee表中所有数据
    //List 中的1个 Map 对应数据库的 1行数据
    //Map 中的 key 对应数据库的字段名，value 对应数据库的字段值
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation("人员查询接口")
    public List<Map<String, Object>> userList() {
        String sql = "select * from pf_org_user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }

    //新增一个用户
    @GetMapping("/add")
    public String addUser() {
        //插入语句，注意时间问题
        String sql = "INSERT INTO pf_org_user(ID,NAME,CODE,SEX ,CREATETIME) VALUE(REPLACE(UUID(),'-',''),'test','1',0,SYSDATE())";
        jdbcTemplate.update(sql);
        //查询
        return "addOk";
    }

    //修改用户信息
    @GetMapping("/update/{code}")
    public String updateUser(@ApiParam("这个code会被返回") @PathVariable("code") String code) {
        //插入语句
        String sql = "update pf_org_user set name=?,SEX=? where code=" + code;
        //数据
        Object[] objects = new Object[2];
        objects[0] = "test1";
        objects[1] = 0;
        jdbcTemplate.update(sql, objects);
        //查询
        return "updateOk(code:" + code + ")";
    }

    //删除用户
    @GetMapping("/delete/{code}")
    public String delUser(@PathVariable("code") int code) {
        //插入语句
        String sql = "delete from pf_org_user where code=?";
        jdbcTemplate.update(sql, code);
        //查询
        return "deleteOk";
    }
}
