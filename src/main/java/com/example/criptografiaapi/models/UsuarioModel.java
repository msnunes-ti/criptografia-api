package com.example.criptografiaapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(unique = true)
    String usuario;

    @NotNull
    String nome;

    @NotNull
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String senha;

    @NotNull
    Integer senhaCriptografada;

    @NotNull
    String email;

    @NotNull
    UUID token;

    @NotNull
    Boolean isAtivo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioModel)) return false;
        UsuarioModel usuarioModel1 = (UsuarioModel) o;
        return Objects.equals(getUsuario(), usuarioModel1.getUsuario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsuario());
    }
}
