package com.example.demo.auth;

import com.example.demo.exceptions.CustomException;
import com.example.demo.exceptions.enums.ErrorCode;
import com.example.demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.demo.entities.user.User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return new CustomUserDetails(user.get().getId(), user.get().getUsername(), user.get().getPassword(), getAuthorities(user.get()));
    }


    private Collection<? extends GrantedAuthority> getAuthorities(com.example.demo.entities.user.User user) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRole()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}



