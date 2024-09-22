package com.example.demo.services;

import com.example.demo.dto.service.request.UserRegisterRequest;
import com.example.demo.dto.service.request.UserSignInRequest;
import com.example.demo.entities.user.User;
import com.example.demo.exceptions.CustomException;
import com.example.demo.exceptions.enums.ErrorCode;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.transformer.entity.UserEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.demo.exceptions.enums.ErrorCode.USER_EXISTS;
import static com.example.demo.exceptions.enums.ErrorCode.USER_NOT_FOUND;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void signUp(UserRegisterRequest request) {
        if (userRepository.findByUsername(request.getUserName()).isPresent()) {
            throw new CustomException(USER_EXISTS);
        }
        User user = UserEntityTransformer.transform(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


}
