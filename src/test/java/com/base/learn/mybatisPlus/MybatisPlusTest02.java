package com.base.learn.mybatisPlus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.leran.mybatisPlus.entiy.User;
import com.base.leran.mybatisPlus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class MybatisPlusTest02 {
    @Autowired
    private UserMapper userMapper;

    /**
     * 自定义接口,并且可以使用mybatis plus 的wrapper
     */

    @Test
    void selectByMyWrapper() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name").isNotNull("email").ge("age", 2);
        List<User> userList = userMapper.selectByMyWrapper(wrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 通过注解自定义sql
     */
    @Test
    void selectByName() {
        List<User> users = userMapper.selectByName("李四");
        users.forEach(System.out::println);
    }

    @Test
    void selectByMyWrapperMapper() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name").isNotNull("email").ge("age", 2);
        List<User> userList = userMapper.selectByMyWrapperMapper(wrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 通过注解自定义sql
     */
    @Test
    void selectByNameMapper() {
        List<User> users = userMapper.selectByNameMapper("李四");
        users.forEach(System.out::println);
    }


    @Test
    void test3() {
// 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age", 20, 30); // 区间
        Integer count = userMapper.selectCount(wrapper);// 查询结果数
        System.out.println(count);
    }

    // 模糊查询
    @Test
    void test4() {
// 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
// 左和右 t%
        wrapper
                .notLike("name", "e")
                .likeRight("email", "t");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    // 模糊查询
    @Test
    void test5() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // id 在子查询中查出来
        wrapper.inSql("id", "select id from user where id<3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    //测试六
    @Test
    void test6() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 通过id进行排序
        wrapper.orderByAsc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

}
