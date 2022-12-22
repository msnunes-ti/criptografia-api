package com.example.criptografiaapi.configs;

import com.example.criptografiaapi.dtos.UsuarioLogadoDTO;
import com.example.criptografiaapi.interceptor.Interceptor;
import com.example.criptografiaapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    UsuarioLogadoDTO usuarioLogadoDTO;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor(jwtUtil, usuarioLogadoDTO, usuarioRepository))
                .addPathPatterns("/**"); // Para todas as rotas = ("/**") se não: ("/login", "/usuarios", ...)
    }
}
