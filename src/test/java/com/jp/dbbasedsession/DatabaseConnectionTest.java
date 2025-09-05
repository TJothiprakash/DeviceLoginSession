package com.jp.dbbasedsession;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
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
    @BeforeAll
    static void setup() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        setIfMissing("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
        setIfMissing("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
        setIfMissing("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
        setIfMissing("SPRING_SECURITY_USER_NAME", dotenv.get("SPRING_SECURITY_USER_NAME"));
        setIfMissing("SPRING_SECURITY_USER_PASSWORD", dotenv.get("SPRING_SECURITY_USER_PASSWORD"));
    }

    private static void setIfMissing(String key, String value) {
        if (System.getProperty(key) == null && System.getenv(key) == null && value != null) {
            System.setProperty(key, value);
        }
    }

    @Test
    void testDbConnection() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            assertNotNull(conn, "Connection should not be null");
            System.out.println("Connected to DB successfully!");
        }
    }
}
