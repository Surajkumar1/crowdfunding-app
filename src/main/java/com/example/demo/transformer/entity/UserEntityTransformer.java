package com.example.demo.transformer.entity;

import com.example.demo.dto.service.request.UserRegisterRequest;
import com.example.demo.entities.user.User;
import com.example.demo.enums.UserRole;


public class UserEntityTransformer {

    public static User transform(UserRegisterRequest request) {
        com.example.demo.entities.user.UserRole userRole = com.example.demo.entities.user.UserRole
                .builder().id(request.getRole().getId()).role(request.getRole().name()).build();
        User user = new User();
        user.setUsername(request.getUserName());
        user.setEmail(request.getEmailId());
        user.setPassword(request.getPassword());
        user.setRole(userRole);
        return user;
    }

    public static User entityToDto(User user) {
        return null;
    }

}
