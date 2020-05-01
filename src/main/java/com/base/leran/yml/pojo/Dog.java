package com.base.leran.yml.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data //使用这个注解，就不用再去手写Getter,Setter,equals,canEqual,hasCode,toString等方法了，注解后在编译时会自动加进去
@AllArgsConstructor //使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
@NoArgsConstructor //使用后创建一个无参构造函数
@Component //把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
public class Dog {
    //@Value("旺财") 原生方式
    private String name;
    private Integer age;
}
