package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario buscarUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    public void criarUsuario(CriarUsuarioDTO novoUsuarioDTO) {
        Usuario buscarUsuario = buscarUsuario(novoUsuarioDTO.getUsuario());
        if (buscarUsuario != null) {
            throw new RuntimeException("Usuário já cadastrado.");
        }
        Usuario usuario = new Usuario();
        usuario.setUsuario(novoUsuarioDTO.getUsuario());

    }
}
