package com.example.criptografiaapi.dtos;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecodificarCifraDeCesarDTO {

    private String mensagem;

    private String decricao;

    private Integer senhaCriptografa;

//    @NotNull
//    private Long usuarioId;
}
