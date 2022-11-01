package com.example.criptografiaapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecodificarCifraDeCesarDTO {

    private String mensagem;

    private Integer senha;
}
