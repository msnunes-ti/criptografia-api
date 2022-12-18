package com.example.criptografiaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
//                .antMatchers(HttpMethod.GET, "/usuarios/**").hasAnyRole("CONVIDADO", "ADMIN", "USER")
//                .antMatchers(HttpMethod.POST, "/usuarios/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.PUT, "/usuarios/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.PATCH, "/usuarios/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.GET, "/v2/cifras/**").hasAnyRole("CONVIDADO", "ADMIN", "USER")
//                .antMatchers(HttpMethod.POST, "/v2/cifras/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.PUT, "/v2/cifras/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.PATCH, "/v2/cifras/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers(HttpMethod.DELETE, "/v2/cifras/**").hasRole("ADMIN")
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
