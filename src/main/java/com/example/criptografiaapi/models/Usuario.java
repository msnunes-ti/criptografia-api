package com.example.criptografiaapi.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(unique = true)
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
    UUID token;

    @NotNull
    Boolean isAtivo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario1 = (Usuario) o;
        return Objects.equals(getUsuario(), usuario1.getUsuario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsuario());
    }
}
