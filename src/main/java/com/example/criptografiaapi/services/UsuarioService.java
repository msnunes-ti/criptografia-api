package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioDTO;
import com.example.criptografiaapi.mappers.UsuarioMapper;
import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    private Usuario buscarUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    public Usuario buscarPorUsuario(String usuario) {
        Usuario usuario1 = usuarioRepository.findByUsuario(usuario);
        return usuario1;
    }

    public Usuario criarUsuario(CriarUsuarioDTO criarUsuarioDTO) {
        Usuario buscarUsuario = buscarUsuario(criarUsuarioDTO.getUsuario());
        if (buscarUsuario.getUsuario().equalsIgnoreCase(criarUsuarioDTO.getUsuario())) {
            throw new RuntimeException("Usuário já cadastrado.");
        }
        Usuario usuario = UsuarioMapper.toUsuario(criarUsuarioDTO);
        usuario.setIsAtivo(true);
        usuario.setToken(UUID.randomUUID());
        usuarioRepository.save(usuario);
        return usuario;
    }
}
