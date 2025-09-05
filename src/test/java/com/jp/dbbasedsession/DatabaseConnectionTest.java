package com.jp.dbbasedsession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testDbConnection() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            assertNotNull(conn, "Connection should not be null");
            System.out.println("Connected to DB successfully!");
        }
    }
}
