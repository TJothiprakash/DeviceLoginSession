package com.jp.dbbasedsession.security.controller;

import com.jp.dbbasedsession.security.entity.User;
import com.jp.dbbasedsession.security.repository.UserRepository;
import com.jp.dbbasedsession.security.util.JwtUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {
        logger.info("Login Request: " + request.toString());
        logger.info("email : " + request.get("useremail") + " Password : " + request.get("password"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.get("useremail"), request.get("password"))
        );
        logger.info("Authentication successful");
        logger.info("generating jwt token");
        String jwttoken = jwtUtil.generateToken(request.get("username"));
        logger.info("token generated : " + jwttoken);
        return jwttoken;
    }
}
