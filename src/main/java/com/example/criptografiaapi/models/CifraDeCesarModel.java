package com.example.criptografiaapi.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "cifra_cesar")
public class CifraDeCesarModel{

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "mensagem", length = 400)
    private String mensagem;

    @Column(name = "descricao", length = 100)
    private String descricao;

    @NotNull
    @Column(name = "data_codificacao")
    private LocalDateTime dataDaCodificacao;

    @NotNull
    @Column(name = "usuario_id")
    private Long usuarioId;
}
