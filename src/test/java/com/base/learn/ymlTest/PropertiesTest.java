package com.base.learn.ymlTest;

import com.base.leran.yml.pojo.People;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PropertiesTest {
    @Autowired
    private People people;

    @Test
    void test() {
        System.out.println(people);
    }
}
