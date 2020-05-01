package com.base.leran.yml.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data //使用这个注解，就不用再去手写Getter,Setter,equals,canEqual,hasCode,toString等方法了，注解后在编译时会自动加进去
@AllArgsConstructor //使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
@NoArgsConstructor //使用后创建一个无参构造函数
@Component //把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
@ConfigurationProperties(prefix = "person")
/*
@ConfigurationProperties作用：
将配置文件中配置的每一个属性的值，映射到这个组件中；
告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定
参数 prefix = “person” : 将配置文件中的person下面的所有属性一一对应
*/
@Validated // 数据校验 JSR-303
/**
 * 空检查
 * @Null 验证对象是否为null
 * @NotNull 验证对象是否不为null, 无法查检长度为0的字符串
 * @NotBlank 检查约束字符串是不是Null还有被Trim的长度是否大于0, 只对字符串, 且会去掉前后空格.
 * @NotEmpty 检查约束元素是否为NULL或者是EMPTY.
 *
 * Booelan检查
 * @AssertTrue 验证 Boolean 对象是否为 true
 * @AssertFalse 验证 Boolean 对象是否为 false
 *
 * 长度检查
 * @Size(min=, max=) 验证对象（Array,Collection,Map,String）长度是否在给定的范围之内
 * @Length(min=, max=) string is between min and max included.
 *
 * 日期检查
 * @Past 验证 Date 和 Calendar 对象是否在当前时间之前
 * @Future 验证 Date 和 Calendar 对象是否在当前时间之后
 * @Pattern 验证 String 对象是否符合正则表达式的规则
 */
public class Person {
    private String name;
    private Integer age;
    private Boolean happy;
    private Date birth;
    @Email(message = "邮箱格式错误") //name必须是邮箱格式
    private String email;
    private Map<String, Object> maps;
    private List<Object> lists;
    private Dog dog;
}
