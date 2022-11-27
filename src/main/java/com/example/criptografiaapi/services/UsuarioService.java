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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    private Optional<Usuario> buscarUsuario(String usuario) {
        return Optional.ofNullable(usuarioRepository.findByUsuario(usuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado!")));
    }

    private Optional<Usuario> buscarUsuarioPeloId(Long id) {
        return Optional.of(usuarioRepository.findById(id)).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public List<UsuarioSensivelDTO> buscarTodosUsuarios() {
        return  UsuarioMapper.toUsuarioSensivelDTOList(usuarioRepository.findAll());
    }

    public ResponseEntity<Object> verificarSeExisteUsuario(String usuario) {
        Optional<Usuario> usuario1 = usuarioRepository.findByUsuario(usuario);
        if (usuario1.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.resolve(204));
        }
        return new ResponseEntity<>(HttpStatus.resolve(200));
    }

    public void criarUsuario(@NotNull CriarUsuarioDTO criarUsuarioDTO) {
        Optional<Usuario> buscarUsuario = buscarUsuario(criarUsuarioDTO.getUsuario());
        if (buscarUsuario.get().getUsuario().equalsIgnoreCase(criarUsuarioDTO.getUsuario())) {
            throw new RuntimeException("Usuário já cadastrado.");
        }
        Usuario usuario = UsuarioMapper.toUsuario(criarUsuarioDTO);
        usuario.setIsAtivo(true);
        usuario.setSenha(bCryptPasswordEncoder.encode(criarUsuarioDTO.getSenha()));
        usuario.setToken(UUID.randomUUID());
        usuarioRepository.save(usuario);
    }

    public void atualizarUsuario(@NotNull Long id, @NotNull AtualizarUsuarioDTO atualizarUsuarioDTO) {
        Optional<Usuario> usuario = buscarUsuarioPeloId(id);
        ResponseEntity<Object> usuario1 = verificarSeExisteUsuario(atualizarUsuarioDTO.getUsuario());
        if(usuario1.getStatusCodeValue() == 200) {
            throw new RuntimeException("Esse usuário já está cadastrado.");
        }
        usuario.get().setUsuario(atualizarUsuarioDTO.getUsuario());
        usuario.get().setNome(atualizarUsuarioDTO.getNome());
        usuario.get().setSenha(atualizarUsuarioDTO.getSenha());
        usuario.get().setSenhaCriptografada(atualizarUsuarioDTO.getSenhaCriptografada());
        usuario.get().setEmail(atualizarUsuarioDTO.getEmail());
        usuario.get().setIsAtivo(atualizarUsuarioDTO.getIsAtivo());
        usuario.get().setToken(UUID.randomUUID());
        usuarioRepository.save(usuario.get());
    }
}
