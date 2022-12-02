package com.example.criptografiaapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSensivelDTO {

    Long id;

    String username;

    String nome;

    String email;

    Boolean isAtivo;
}
