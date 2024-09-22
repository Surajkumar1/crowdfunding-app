package com.example.demo.dto.service.request;

import com.example.demo.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequest {
    String userName;
    String emailId;
    String password;
    UserRole role;
}
