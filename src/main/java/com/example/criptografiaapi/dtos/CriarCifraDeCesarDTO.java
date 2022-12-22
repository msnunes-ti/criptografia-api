package com.example.criptografiaapi.dtos;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarCifraDeCesarDTO {

    @NotNull
    private String mensagem;

    private String descricao;
}
