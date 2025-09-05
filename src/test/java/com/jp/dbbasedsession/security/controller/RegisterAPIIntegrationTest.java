package com.jp.dbbasedsession.security.controller;

import com.jp.dbbasedsession.security.entity.User;
import com.jp.dbbasedsession.security.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class RegisterAPIIntegrationTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    /**
     * Runs before each test to set environment properties and prepare test user
     */
    @BeforeEach
    void setup() {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        setIfMissing("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
        setIfMissing("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
        setIfMissing("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
        setIfMissing("SPRING_SECURITY_USER_NAME", dotenv.get("SPRING_SECURITY_USER_NAME"));
        setIfMissing("SPRING_SECURITY_USER_PASSWORD", dotenv.get("SPRING_SECURITY_USER_PASSWORD"));

        // Create test user
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setUseremail("testuser@example.com");
        testUser.setPassword(passwordEncoder.encode("password123"));
    }

    /**
     * Sets a system property only if it’s not already set
     */
    private static void setIfMissing(String key, String value) {
        if (System.getProperty(key) == null && System.getenv(key) == null && value != null) {
            System.setProperty(key, value);
        }
    }

    /**
     * Tests the registration API by saving a user and verifying it exists
     */
    @Test
    void testRegister() {
        userRepo.save(testUser);
        assertTrue(userRepo.findByUseremail("testuser@example.com").isPresent());
    }

    /**
     * Cleanup after each test: delete only the test user
     */
    @AfterEach
    void cleanup() {
        userRepo.findByUseremail("testuser@example.com")
                .ifPresent(user -> userRepo.delete(user));
    }
}
