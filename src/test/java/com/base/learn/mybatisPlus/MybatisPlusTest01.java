package com.base.learn.mybatisPlus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.leran.mybatisPlus.entiy.User;
import com.base.leran.mybatisPlus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class MybatisPlusTest01 {
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询全部
     */
    @Test
    void findByALL() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    /**
     * 根据id查询
     */
    @Test
    void findById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);

    }

    /**
     * 多个id批量查询
     */
    @Test
    void findBatchId() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
        users.forEach(System.out::println);
    }

    /**
     * 多条件查询
     */
    @Test
    void findCondition() {
        HashMap<String, Object> map = new HashMap<>();

        // 自定义查询
        map.put("name", "Sandy");
        map.put("age", 21);
        userMapper.selectByMap(map);
    }

    /**
     * 分页查询
     */

    @Test
    void findPages() {
        // 参数1： 当前页  参数2：页面大小
        Page<User> userPage = new Page<>(1, 2);
        userMapper.selectPage(userPage, null);
        System.out.println("总共的条数:" + userPage.getTotal());
        userPage.getRecords().forEach(System.out::println);
    }

    /**
     * 插入
     */
    @Test
    void insert() {
        User user = new User();
        user.setName("张三1");
        user.setAge(11);
        user.setEmail("123@qq.com");
        int insertCount = userMapper.insert(user);
        System.out.println(insertCount);
        System.out.println(user);
    }

    /**
     * 更新
     */
    @Test
    void updateUse() {
        User user = new User();
        user.setId(1251823300758220802L);
        user.setName("李四1");
        userMapper.updateById(user);
        System.out.println(user);
    }

    /**
     * 删除
     */
    @Test
    void deleteByid() {
        userMapper.deleteById(1L);
    }

    /**
     * 批量删除
     */
    @Test
    void deteteBatchId() {
        userMapper.deleteBatchIds(Arrays.asList(1L, 2L));
    }

    /**
     * 通过map删除
     */
    @Test
    void detletedMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        userMapper.deleteByMap(map);
    }

    /**
     * 测试乐观锁成功
     */
    @Test
    void versionTestSusser() {
        // 1 查询用户信息
        User user = userMapper.selectById(1L);
        // 2 修改用户信息
        user.setName("name1");
        user.setEmail("1111@qq.como");
        // 3 执行更新操作
        userMapper.updateById(user);
        System.out.println(user);
    }

    /**
     * 测试乐观锁失败！ 多线程下
     */
    @Test
    void versionTestFail() {
        // 线程1
        User user = userMapper.selectById(1L);
        user.setName("name2");
        ;
        user.setEmail("2343@qq.com");

        // 线程2 执行插队操作

        User user1 = userMapper.selectById(1L);
        user1.setEmail("name2222");
        user1.setEmail("233333@qq.com");
        userMapper.updateById(user1);

        userMapper.updateById(user); // 如果没有乐观锁快就会覆盖

    }
}
