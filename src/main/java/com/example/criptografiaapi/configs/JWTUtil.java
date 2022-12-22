package com.example.criptografiaapi.configs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.example.criptografiaapi.models.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JWTUtil {

    @Value("${springbootjjwt.jjwt.token-secret}")
    private String tokenSecret;

    @Value("${springbootjjwt.expiration}")
    private String expirationTokenTime;

    public Long getIdDoUsuarioFromToken(String token) {
        Map<String, Claim> allClaimsFromToken = JWT.decode(token).getClaims();
        Long claimValue = allClaimsFromToken.get("id").asLong();
        if(claimValue == null) {
            throw new RuntimeException("Chave não encontrada nas Claims");
        }
        return claimValue;
    }

    private Map<String, Object> separarString(String texto) {
        String regex = "([^,\\:]+)\\:([^\\,]+)";
        Map<String, Object> resultado = new HashMap<>();
        Pattern parte = Pattern.compile(regex);
        Matcher matcher = parte.matcher(texto);
        while (matcher.find()) {
            String chave = matcher.group(1);
            String valor = matcher.group(2);
            resultado.put(chave.trim(), valor.trim());
        }
        return resultado;
    }

    public Date getExpirationDateFromToken(String token) {
        return JWT.decode(token).getExpiresAt();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String doGenerateToken(Usuario usuario) {
        long expirationTimeLong = Long.parseLong(String.valueOf(expirationTokenTime));
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);
        return JWT.create()
                .withClaim("id", usuario.getId() )
                .withClaim("username", usuario.getUsername())
                .withClaim("email", usuario.getEmail())
                .withSubject(usuario.getNome())
                .withIssuedAt(createdDate)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(tokenSecret));
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}