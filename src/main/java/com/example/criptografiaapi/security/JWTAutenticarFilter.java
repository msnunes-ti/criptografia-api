package com.example.criptografiaapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.criptografiaapi.data.DetalheUsuarioData;
import com.example.criptografiaapi.models.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Getter
@Setter
public class JWTAutenticarFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public static final int TOKEN_EXPIRACAO = 900_000;
    public static final String TOKEN_SENHA = "c87a404c-f386-4961-be31-30b29287d316";

    public JWTAutenticarFilter(AuthenticationManager authenticationManager, AuthenticationManager authenticationManager1) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager1;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    usuario.getRoles()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar o Usuário", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        DetalheUsuarioData usuarioData = (DetalheUsuarioData) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(usuarioData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
                .sign(Algorithm.HMAC512(TOKEN_SENHA));
        response.getWriter().write(token);
        response.getWriter().flush();
    }
}
