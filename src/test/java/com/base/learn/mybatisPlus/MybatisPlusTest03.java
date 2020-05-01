package com.base.learn.mybatisPlus;

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
   void addTest(){
       PfOrgUser pfOrgUser= new PfOrgUser();
       pfOrgUser.setCode(1001);
       pfOrgUser.setName("test");
       pfOrgUser.setId("1");
       pfOrgUserService.addUser(pfOrgUser);
    }
    @Test
    void deleteTest(){
        pfOrgUserMapper.deleteById("1");
    }


}
