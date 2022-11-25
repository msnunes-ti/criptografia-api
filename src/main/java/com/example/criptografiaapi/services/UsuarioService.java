package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.mappers.UsuarioMapper;
import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.repositories.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Getter
@Setter
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    private Optional<Usuario> buscarUsuario(String usuario) {
        return Optional.ofNullable(usuarioRepository.findByUsuario(usuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado!")));
    }

    public ResponseEntity<Object> buscarPorUsuario(String usuario) {
        Optional<Usuario> usuario1 = usuarioRepository.findByUsuario(usuario);
        if (!usuario1.isPresent()) {
            assert HttpStatus.resolve(204) != null;
            return new ResponseEntity<>(HttpStatus.resolve(204));
        }
        return null;
    }

    public Usuario criarUsuario(@NotNull CriarUsuarioDTO criarUsuarioDTO) {
        Optional<Usuario> buscarUsuario = buscarUsuario(criarUsuarioDTO.getUsuario());
        if (buscarUsuario.get().getUsuario().equalsIgnoreCase(criarUsuarioDTO.getUsuario())) {
            throw new RuntimeException("Usuário já cadastrado.");
        }
        Usuario usuario = UsuarioMapper.toUsuario(criarUsuarioDTO);
        usuario.setIsAtivo(true);
        usuario.setToken(UUID.randomUUID());
        usuarioRepository.save(usuario);
        return usuario;
    }
}
