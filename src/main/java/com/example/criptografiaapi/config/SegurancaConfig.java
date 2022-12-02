package com.example.criptografiaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SegurancaConfig {


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static final int TOKEN_EXPIRACAO = 900_000;

    public static final String TOKEN_SENHA = "c87a404c-f386-4961-be31-30b29287d316";
}
