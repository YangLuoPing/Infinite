package com.base.learn.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class JdbcTest {
    @Autowired
    DataSource dataSource;

    @Test
    void contextLoada() throws SQLException {
        System.out.println("------------  dataSource  信息-------------------");
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }
}
