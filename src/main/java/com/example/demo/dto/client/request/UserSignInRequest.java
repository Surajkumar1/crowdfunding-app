package com.example.demo.dto.client.request;

import lombok.Data;

@Data
public class UserSignInRequest {
    String userName;
    String password;
}
