package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.AtualizarUsuarioDTO;
import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.mappers.UsuarioMapper;
import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    private Optional<Usuario> buscarUsuario(String usuario) {
        return Optional.ofNullable(usuarioRepository.findByUsuario(usuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado!")));
    }

    private Optional<Usuario> buscarUsuarioPeloId(Long id) {
        return Optional.of(usuarioRepository.findById(id)).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public ResponseEntity<Object> buscarPorUsuario(String usuario) {
        Optional<Usuario> usuario1 = usuarioRepository.findByUsuario(usuario);
        if (usuario1.isEmpty()) {
            assert HttpStatus.resolve(204) != null;
            return new ResponseEntity<>(HttpStatus.resolve(204));
        }
        return null;
    }

    public void criarUsuario(@NotNull CriarUsuarioDTO criarUsuarioDTO) {
        Optional<Usuario> buscarUsuario = buscarUsuario(criarUsuarioDTO.getUsuario());
        if (buscarUsuario.get().getUsuario().equalsIgnoreCase(criarUsuarioDTO.getUsuario())) {
            throw new RuntimeException("Usuário já cadastrado.");
        }
        Usuario usuario = UsuarioMapper.toUsuario(criarUsuarioDTO);
        usuario.setIsAtivo(true);
        usuario.setToken(UUID.randomUUID());
        usuarioRepository.save(usuario);
    }

    public void atualizarUsuario(@NotNull Long id, @NotNull AtualizarUsuarioDTO atualizarUsuarioDTO) {
        Optional<Usuario> usuario = buscarUsuarioPeloId(id);
        usuario.get().setNome(atualizarUsuarioDTO.getNome());
        usuario.get().setSenha(atualizarUsuarioDTO.getSenha());
        usuario.get().setSenhaCriptografada(atualizarUsuarioDTO.getSenhaCriptografada());
        usuario.get().setEmail(atualizarUsuarioDTO.getEmail());
        usuario.get().setIsAtivo(atualizarUsuarioDTO.getIsAtivo());
        usuario.get().setToken(UUID.randomUUID());
        usuarioRepository.save(usuario.get());
    }
}
