package com.example.criptografiaapi.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    @Column(name = "mensagem", length = 2000)
    private String mensagem;

    @NotNull
    @Column(name = "senha")
    private Integer senha;
}
