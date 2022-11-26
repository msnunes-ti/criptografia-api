package com.example.criptografiaapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarUsuarioDTO {

    String usuario;

    String nome;

    String senha;

    String senhaCriptografada;

    String email;

    Boolean isAtivo;
}
