package com.example.criptografiaapi.dtos;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarUsuarioDTO {

    @NotNull
    String usuario;

    @NotNull
    String nome;

    @NotNull
    String senha;

    @NotNull
    Integer senhaCriptografada;

    @NotNull
    String email;

}
