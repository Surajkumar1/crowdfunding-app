package com.example.demo.utils;

import com.example.demo.auth.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class UserContextUtility {

    public static Optional<CustomUserDetails> fetchUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            if(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser")){
                return Optional.empty();
            }
            return Optional.of((CustomUserDetails) authentication.getPrincipal());
        }
        return Optional.empty();
    }

    public static Long fetchUserId() {
        Optional<CustomUserDetails> userDetails = fetchUserDetails();
        return userDetails.map(CustomUserDetails::getUserId).orElse(null);
    }

}


