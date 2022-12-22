package com.example.criptografiaapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DecodificarCifraDeCesarDTO {

    private Long id;

    private String mensagem;

    private String decricao;

    private Long usuarioId;

    private LocalDateTime dataDaCodificacao;
}
