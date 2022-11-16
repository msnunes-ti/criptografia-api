package com.example.criptografiaapi.models;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String usuario;

    @NotNull
    String nome;

    @NotNull
    String senha;

    @NotNull
    String senhaCriptografada;

    @NotNull
    String email;

    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID token;
    
    @NotNull
    Boolean isAtivo;

}
