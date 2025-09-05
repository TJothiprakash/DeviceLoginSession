package com.jp.dbbasedsession;

import com.jp.dbbasedsession.security.entity.User;
import com.jp.dbbasedsession.security.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RegisterAPIIntegrationTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        registry.add("spring.datasource.url", () -> dotenv.get("SPRING_DATASOURCE_URL"));
        registry.add("spring.datasource.username", () -> dotenv.get("SPRING_DATASOURCE_USERNAME"));
        registry.add("spring.datasource.password", () -> dotenv.get("SPRING_DATASOURCE_PASSWORD"));
        registry.add("spring.security.user.name", () -> dotenv.get("SPRING_SECURITY_USER_NAME"));
        registry.add("spring.security.user.password", () -> dotenv.get("SPRING_SECURITY_USER_PASSWORD"));
    }

    @BeforeEach
    void setup() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setUseremail("testuser@example.com");
        testUser.setPassword(passwordEncoder.encode("password123"));
    }

    @Test
    void testRegister() {
        userRepo.save(testUser);
        assertTrue(userRepo.findByUseremail("testuser@example.com").isPresent());
    }

    @AfterEach
    void cleanup() {
        userRepo.findByUseremail("testuser@example.com")
                .ifPresent(user -> userRepo.delete(user));
    }
}
