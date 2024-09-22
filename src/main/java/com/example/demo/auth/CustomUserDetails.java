package com.example.demo.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long userId;  // Add user ID field
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Implement as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Implement as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Implement as needed
    }

    @Override
    public boolean isEnabled() {
        return true;  // Implement as needed
    }
}
