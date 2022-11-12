package com.example.criptografiaapi.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "cifra_cesar")
public class CifraDeCesar {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "mensagem", length = 200)
    private String mensagem;

    @Column(name = "descricao", length = 200)
    private String decricao;

    @NotNull
    @Column(name = "senha")
    private Integer senha;

    @NotNull
    @Column(name = "data_codificacao")
    private LocalDateTime dataDaCodificacao;

}
