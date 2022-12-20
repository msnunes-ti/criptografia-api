package com.example.criptografiaapi.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.criptografiaapi.models.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

//    @Value("${springbootjjwt.jjwt.token-secret}")
//    public static String secret;
    public static final String TOKEN_SENHA = "c87a404c-f386-4961-be31-30b29287d316";

//    @Value("${springbootjjwt.expiration}")
//    private static String expirationTime;
    public static final int TOKEN_EXPIRACAO = 900_000;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(TOKEN_SENHA).build().parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public static String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", usuario.getUsername());
        claims.put("email", usuario.getEmail());
        return doGenerateToken(claims, usuario.getUsername());
    }

    private static String doGenerateToken(Map<String, Object> claims, String username) {
        long expirationTimeLong = Long.parseLong(String.valueOf(TOKEN_EXPIRACAO));
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);
        return JWT.create()
                .withClaim("claims", claims)
                .withSubject(username)
                .withIssuedAt(createdDate)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(TOKEN_SENHA));
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}