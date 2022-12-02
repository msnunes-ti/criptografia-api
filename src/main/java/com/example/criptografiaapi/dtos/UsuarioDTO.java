package com.example.criptografiaapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UsuarioDTO {

    Long id;

    String username;

    String nome;

    String password;

    Integer senhaCriptografada;

    String email;

    UUID token;

    Boolean isAtivo;
}
