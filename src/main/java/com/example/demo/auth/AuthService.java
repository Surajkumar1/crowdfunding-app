package com.example.demo.auth;

import com.example.demo.entities.user.User;
import com.example.demo.exceptions.CustomException;
import com.example.demo.exceptions.enums.ErrorCode;
import com.example.demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            String token = jwtUtil.generateToken(userDetails);
            redisService.setValue(token, username);
            return token;
        } else {
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

}
