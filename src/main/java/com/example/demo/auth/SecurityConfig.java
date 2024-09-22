package com.example.demo.auth;

import com.example.demo.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.demo.utils.Constants.COMMA;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Value("${url.auth.skip}")
    private String skipAuthUrls;

    @Value("${url.auth.innovator.skip}")
    private String innovatorSkipAuthUrls;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] a = skipAuthUrls.trim().split(COMMA);
        http.csrf().disable()
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(skipAuthUrls.trim().split(COMMA)).permitAll()
                        .requestMatchers(innovatorSkipAuthUrls.trim().split(COMMA)).hasRole(UserRole.INNOVATOR.name())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
