package com.example.criptografiaapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodificarCifraDeCesarDTO {

    private String mensagem;

    private Integer senha;
}
