package com.example.criptografiaapi.dtos;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class CodificarCifraDeCesarDTO {

    @NotNull
    private String mensagem;

    private String descricao;

    @NotNull
    @Column(name = "usuario_id")
    private Long usuarioId;
}
