package com.example.demo.enums;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public enum UserRole {
    USER(1),
    INNOVATOR(2),
    ADMIN(3);

    Integer id;

    UserRole(Integer id){
        this.id = id;
    }

}
