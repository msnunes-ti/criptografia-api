package com.example.criptografiaapi.dtos;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodificarCifraDeCesarDTO {

    @NotNull
    private String mensagem;

    private String descricao;

    @NotNull
    private Integer senha;
}
