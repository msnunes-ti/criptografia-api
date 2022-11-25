package com.example.criptografiaapi.repositories;

import com.example.criptografiaapi.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsuario(String usuario);
}

