package com.example.demo.transformer.service;

import com.example.demo.dto.service.request.UserRegisterRequest;
import com.example.demo.dto.service.request.UserSignInRequest;

public class UserServiceTransformer {

    public static UserSignInRequest transform(com.example.demo.dto.client.request.UserSignInRequest request){
        return UserSignInRequest.builder()
                .userName(request.getUserName())
                .password(request.getPassword()).build();
    }

    public static UserRegisterRequest transform(com.example.demo.dto.client.request.UserRegisterRequest request){
        return UserRegisterRequest.builder()
                .emailId(request.getEmailId())
                .password(request.getPassword())
                .userName(request.getUserName())
                .role(request.getRole()).build();
    }

}
