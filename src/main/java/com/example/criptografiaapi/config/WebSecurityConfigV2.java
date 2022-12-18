package com.example.criptografiaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfigV2 {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/usuarios/**").hasAnyRole("ROLE_CONVIDADO", "ROLE_ADMIN", "ROLE_USER")
                .antMatchers(HttpMethod.POST, "/usuarios/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                .antMatchers(HttpMethod.PUT, "/usuarios/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                .antMatchers(HttpMethod.PATCH, "/usuarios/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                .antMatchers(HttpMethod.DELETE, "/usuarios/**").hasAnyRole("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/v2/cifras/**").hasAnyRole("ROLE_CONVIDADO", "ROLE_ADMIN", "ROLE_USER")
                .antMatchers(HttpMethod.POST, "/v2/cifras/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                .antMatchers(HttpMethod.PUT, "/v2/cifras/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                .antMatchers(HttpMethod.PATCH, "/v2/cifras/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                .antMatchers(HttpMethod.DELETE, "/v2/cifras/**").hasAnyRole("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
