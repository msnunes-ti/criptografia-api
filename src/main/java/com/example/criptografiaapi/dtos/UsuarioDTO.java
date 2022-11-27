package com.example.criptografiaapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UsuarioDTO {

    Long id;

    String usuario;

    String nome;

    String senha;

    Integer senhaCriptografada;

    String email;

    UUID token;

    Boolean isAtivo;
}
