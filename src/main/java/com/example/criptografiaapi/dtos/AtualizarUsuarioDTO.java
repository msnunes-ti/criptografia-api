package com.example.criptografiaapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarUsuarioDTO {

    String username;

    String nome;

    String password;

    Integer senhaCriptografada;

    String email;

    Boolean isAtivo;
}
