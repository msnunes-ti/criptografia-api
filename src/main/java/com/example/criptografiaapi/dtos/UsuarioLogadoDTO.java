package com.example.criptografiaapi.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.ManagedBean;

@ManagedBean
@SessionScope
@Getter
@Setter
public class UsuarioLogadoDTO {

    Long id;

    String username;

    String nome;

    Integer senhaCriptografada;

    String email;

    @Override
    public String toString() {
        return "UsuarioLogadoDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", senhaCriptografada=" + senhaCriptografada +
                ", email='" + email + '\'' +
                '}';
    }
}
