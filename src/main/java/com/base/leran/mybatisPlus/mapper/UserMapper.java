package com.base.leran.mybatisPlus.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.base.leran.mybatisPlus.entiy.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 继承对应的mapper上集成基本类 BaseMapper
 * 所有的CRUD的操作都已自动编写完成，不需要在自己去填写
 * id会自动生成
 * 自定义mapper文件必须在pox加如下配置
 *  https://www.cnblogs.com/angel-devil/p/12462944.html
 *
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     *
     * 如果自定义的方法还希望能够使用MP提供的Wrapper条件构造器，则需要如下写法
     *
     * @param userWrapper
     * @return
     */
    @Select("SELECT * FROM user ${ew.customSqlSegment}")
    List<User> selectByMyWrapper(@Param(Constants.WRAPPER) Wrapper<User> userWrapper);

    /**
     * 和Mybatis使用方法一致
     * @param name
     * @return
     */
    @Select("SELECT * FROM user where name = #{name}")
    List<User> selectByName(@Param("name") String name);

    /**
     * 根据mybatis配置文件自定义方法
     * 如果自定义的方法还希望能够使用MP提供的Wrapper条件构造器，则需要如下写法
     *
     * @param userWrapper
     * @return
     */
    List<User> selectByMyWrapperMapper(@Param(Constants.WRAPPER) Wrapper<User> userWrapper);

    /**
     * 根据mybatis配置文件自定义方法
     * 和Mybatis使用方法一致
     * @param name
     * @return
     */
    List<User> selectByNameMapper(@Param("name") String name);


}
