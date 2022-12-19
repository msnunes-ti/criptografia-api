//package com.example.criptografiaapi.config;
//
//import com.auth0.jwt.algorithms.Algorithm;
//import com.example.criptografiaapi.models.Usuario;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import javax.xml.bind.DatatypeConverter;
//import java.security.Key;
//import java.util.*;
//
//@Component
//public class JWTUtil {
//
//    @Value("${springbootjjwt.jjwt.secret}")
//    private String secret;
//
//    @Value("${springbootjjwt.expiration}")
//    private String expirationTime;
//
//    public Claims getAllClaimsFromToken(String token) {
//        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
//    }
//
//    public String getUsernameFromToken(String token) {
//        return getAllClaimsFromToken(token).getSubject();
//    }
//
//    public Date getExpirationDateFromToken(String token) {
//        return getAllClaimsFromToken(token).getExpiration();
//    }
//
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    public String generateToken(Usuario usuario) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("username", usuario.getUsername());
//        claims.put("email", usuario.getEmail());
//        return doGenerateToken(claims, usuario.getUsername());
//    }
//
//    private String doGenerateToken(Map<String, Object> claims, String username) {
//        Long expirationTimeLong = Long.parseLong(expirationTime); //in second
//
//        final Date createdDate = new Date();
//        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
//        byte[] senha = Base64.getDecoder().decode(secret);
//        SecretKey key = Keys.hmacShaKeyFor(senha);
//
//
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(username)
//                .setIssuedAt(createdDate)
//                .setExpiration(expirationDate)
//                .signWith((Key) Algorithm.HMAC512(Arrays.toString(new SecretKey[]{key})))
//                .compact();
//    }
//
//    public Boolean validateToken(String token) {
//        return !isTokenExpired(token);
//    }
//}
