package com.base.leran.jdbc.servcie.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.base.leran.jdbc.servcie.JdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JdbcServiceImpl implements JdbcService {
    /**
     * Spring Boot 默认提供了数据源，默认提供了 org.springframework.jdbc.core.JdbcTemplate
     * JdbcTemplate 中会自己注入数据源，用于简化 JDBC操作
     * 还能避免一些常见的错误,使用起来也不用再自己来关闭数据库连接
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @DS("slave_tempo")
    @Override
    public List<Map<String, Object>> findAll() {
        String sql = "select * from pf_org_user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }
    @DS("slave_tempo")
    public String addUser() {
        //插入语句，注意时间问题
        String sql = "INSERT INTO pf_org_user(ID,NAME,CODE,SEX ,CREATETIME) VALUE(REPLACE(UUID(),'-',''),'test','1',0,SYSDATE())";
        jdbcTemplate.update(sql);
        //查询
        return "addOk";
    }
    public String updateUser(String code) {
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

    public String delUser(Integer code) {
        //插入语句
        String sql = "delete from pf_org_user where code=?";
        jdbcTemplate.update(sql, code);
        //查询
        return "deleteOk";
    }
}
