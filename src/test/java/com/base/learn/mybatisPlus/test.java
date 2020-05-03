package com.base.learn.mybatisPlus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.leran.mybatisPlus.entiy.TElasticsearchIndex;
import com.base.leran.mybatisPlus.mapper.TEsIndexMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {
    @Autowired
    private TEsIndexMapper tEsIndexMapper;

    @Test
    void tesr() {
        Page<TElasticsearchIndex> userPage = new Page<>(1, 2);
        tEsIndexMapper.selectPage(userPage, null);
        System.out.println("总共的条数:" + userPage.getTotal());
        userPage.getRecords().forEach(System.out::println);
    }

}
