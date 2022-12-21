package com.example.criptografiaapi.configs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.criptografiaapi.models.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    @Value("${springbootjjwt.jjwt.token-secret}")
    private String tokenSecret;

    @Value("${springbootjjwt.expiration}")
    private String expirationTokenTime;

    public Map<String, Object> getAllClaimsFromToken(String token) {
        return new HashMap<>(JWT.decode(token).getClaims());
    }

    public String getUsernameFromToken(String token) {
        Map<String, Object> claims = getAllClaimsFromToken(token);
        return claims.get("sub").toString();
    }

    public Date getExpirationDateFromToken(String token) {
        return JWT.decode(token).getExpiresAt();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("username", usuario.getUsername());
        claims.put("email", usuario.getEmail());
        return doGenerateToken(claims, usuario.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {

        long expirationTimeLong = Long.parseLong(String.valueOf(expirationTokenTime));
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);
        return JWT.create()
                .withClaim("claims", claims)
                .withSubject(username)
                .withIssuedAt(createdDate)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(tokenSecret));
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}