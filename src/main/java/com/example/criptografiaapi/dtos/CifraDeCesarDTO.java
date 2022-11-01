package com.example.criptografiaapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CifraDeCesarDTO {

    private Long id;

    private String mensagem;

    private Integer senha;
}
