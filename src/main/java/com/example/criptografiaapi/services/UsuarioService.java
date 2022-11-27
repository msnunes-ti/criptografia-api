package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.AtualizarUsuarioDTO;
import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioSensivelDTO;
import com.example.criptografiaapi.mappers.UsuarioMapper;
import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<Usuario> buscarUsuarioPeloUsuario(String usuario) {
        return Optional.ofNullable(usuarioRepository.findByUsuario(usuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado!")));
    }

    public Usuario buscarUsuarioPeloId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public List<UsuarioSensivelDTO> buscarTodosUsuarios() {
        return  UsuarioMapper.toUsuarioSensivelDTOList(usuarioRepository.findAll());
    }

    public Boolean verificarSeExisteUsuario(String usuario) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByUsuario(usuario);
        return usuarioEncontrado.isPresent();
    }

    @Transactional
    public void criarUsuario(@NotNull CriarUsuarioDTO criarUsuarioDTO) {
        Optional<Usuario> buscarUsuario = usuarioRepository.findByUsuario(criarUsuarioDTO.getUsuario());
        if (buscarUsuario.isPresent()) {
            throw new RuntimeException("Usuário já cadastrado.");
        }
        Usuario usuario = UsuarioMapper.toUsuario(criarUsuarioDTO);
        usuario.setIsAtivo(true);
        usuario.setSenha(bCryptPasswordEncoder.encode(criarUsuarioDTO.getSenha()));
        usuario.setToken(UUID.randomUUID());
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void atualizarUsuarioCompleto(@NotNull Long id, @NotNull AtualizarUsuarioDTO atualizarUsuarioDTO) {
        Usuario usuario = buscarUsuarioPeloId(id);
        usuario.setUsuario(atualizarUsuarioDTO.getUsuario());
        usuario.setNome(atualizarUsuarioDTO.getNome());
        usuario.setSenha(atualizarUsuarioDTO.getSenha());
        usuario.setSenhaCriptografada(atualizarUsuarioDTO.getSenhaCriptografada());
        usuario.setEmail(atualizarUsuarioDTO.getEmail());
        usuario.setIsAtivo(atualizarUsuarioDTO.getIsAtivo());
        usuario.setToken(UUID.randomUUID());
        usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuarioPeloId(id);
        usuarioRepository.delete(usuario);
    }
}
