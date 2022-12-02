package com.example.criptografiaapi.dtos;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarUsuarioDTO {

    @NotNull
    String username;

    @NotNull
    String nome;

    @NotNull
    String password;

    @NotNull
    Integer senhaCriptografada;

    @NotNull
    String email;

}
