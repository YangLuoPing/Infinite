package com.base.learn.ymlTest;

import com.base.leran.yml.pojo.Dog;
import com.base.leran.yml.pojo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class YmlTest1 {
    @Autowired
    private Dog dog;
    @Autowired
    private Person person;

    @Test
    void test1() {
        System.out.println(dog);
    }

    @Test
    void test2() {
        System.out.println(person);
    }
}
