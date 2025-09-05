package com.jp.dbbasedsession.security.controller;

import com.jp.dbbasedsession.security.entity.User;
import com.jp.dbbasedsession.security.repository.UserRepository;
import com.jp.dbbasedsession.security.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("pass123");
        user.setUseremail("john@example.com");
        user.setRole("USER");

        when(passwordEncoder.encode("pass123")).thenReturn("encodedPass");

        String response = authController.register(user);

        assertEquals("User registered successfully", response);
        verify(userRepo, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode("pass123");
    }
    @Test
    void testLogin() {
        Map<String, String> request = new HashMap<>();
        request.put("useremail", "john@example.com");
        request.put("password", "pass123");
        request.put("username", "john");

        when(jwtUtil.generateToken("john")).thenReturn("fake-jwt-token");

        // Correct way for non-void authenticate()
        Authentication authMock = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authMock);

        String token = authController.login(request);

        assertEquals("fake-jwt-token", token);
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtUtil, times(1)).generateToken("john");
    }

}
