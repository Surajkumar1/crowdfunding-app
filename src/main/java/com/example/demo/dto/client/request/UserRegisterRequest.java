package com.example.demo.dto.client.request;

import com.example.demo.enums.UserRole;
import lombok.Data;

@Data
public class UserRegisterRequest {
    String userName;
    String emailId;
    String password;
    UserRole role;
}
