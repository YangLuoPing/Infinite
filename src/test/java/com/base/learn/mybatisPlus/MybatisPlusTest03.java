package com.base.learn.mybatisPlus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.leran.mybatisPlus.entiy.PfOrgUser;
import com.base.leran.mybatisPlus.mapper.PfOrgUserMapper;
import com.base.leran.mybatisPlus.service.PfOrgUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MybatisPlusTest03 {
    @Autowired
    private PfOrgUserMapper pfOrgUserMapper;

    @Autowired
    private PfOrgUserService pfOrgUserService;

    @Test
    void addTest() {
        PfOrgUser pfOrgUser = new PfOrgUser();
        pfOrgUser.setCode(1001);
        pfOrgUser.setName("test");
        pfOrgUser.setId("1");
        pfOrgUserService.addUser(pfOrgUser);
    }

    @Test
    void deleteTest() {
        pfOrgUserMapper.deleteById("1");
    }

    @Test
    void selectTest() {
        Page<Object> page = new Page<>(1, 2);
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
        pfOrgUserMapper.selectList(null);
    }

    /*@Test
    void selectTest02() {
        Page<Object> page = new Page<>(1, 2);
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
        List<PfOrgUser> pfOrgUsers = pfOrgUserMapper.selectList(page, queryWrapper);


    }*/

}
