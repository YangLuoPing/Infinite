package com.base.leran.mybatisPlus.entiy;


import com.baomidou.mybatisplus.annotation.*;
import com.base.utils.dict.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data //使用这个注解，就不用再去手写Getter,Setter,equals,canEqual,hasCode,toString等方法了，注解后在编译时会自动加进去
@AllArgsConstructor //使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
@NoArgsConstructor //使用后创建一个无参构造函数
public class User {

    // 对应数据库中的主键（uuid,自增id，雪花算法，redis,zookeeper） 默认为雪花模型的id
    //@TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    @EnumValue//标记数据库存的值是code
    private SexEnum sex;
    private Integer age;
    private String email;
    // 字段添加填充 需要实现对应的接口
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    @Version // 乐观锁
    private Integer version;
    @TableLogic // 删除字段  有 @TableLogic 则以实体上的为准，忽略全局。 即先查找注解再查找全局，都没有则此表没有逻辑删除。
    private Integer deleted;
}
