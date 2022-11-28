package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.*;
import com.example.criptografiaapi.mappers.UsuarioMapper;
import com.example.criptografiaapi.models.CifraDeCesar;
import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.repositories.CifraDeCesarRepository;
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
    CifraDeCesarRepository cifraDeCesarRepository;

    @Autowired
    CifraDeCesarServiceV2 cifraDeCesarServiceV2;

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

    private void atualizaMensagensDoUsuario(Long id, Integer senhaCriptografadaAntiga, Integer senhaCriptografadaNova) {
        List<CifraDeCesar> cifraDeCesarList = cifraDeCesarRepository.findAllByUsuarioId(id);
        for (CifraDeCesar c: cifraDeCesarList) {
            String mensagemDecodificada = cifraDeCesarServiceV2.decodificarMensagem(c.getMensagem(), senhaCriptografadaAntiga);
            String mensagemRecodificada = cifraDeCesarServiceV2.criptografarMensagem(mensagemDecodificada, senhaCriptografadaNova);
            c.setMensagem(mensagemRecodificada);
            cifraDeCesarRepository.save(c);
        }
    }

    public void atualizarUsuarioCompleto(Long id, AtualizarUsuarioDTO atualizarUsuarioDTO) {
        Usuario usuario = buscarUsuarioPeloId(id);
        atualizaMensagensDoUsuario(usuario.getId(), usuario.getSenhaCriptografada(), atualizarUsuarioDTO.getSenhaCriptografada());
        usuario.setUsuario(atualizarUsuarioDTO.getUsuario());
        usuario.setNome(atualizarUsuarioDTO.getNome());
        usuario.setSenha(atualizarUsuarioDTO.getSenha());
        usuario.setEmail(atualizarUsuarioDTO.getEmail());
        usuario.setSenhaCriptografada(atualizarUsuarioDTO.getSenhaCriptografada());
        usuario.setIsAtivo(atualizarUsuarioDTO.getIsAtivo());
        usuarioRepository.save(usuario);
    }

    public void atualizarSenhaCriptografadaDoUsuario(Long id, Integer senhaCriptografada) {
        Usuario usuario = buscarUsuarioPeloId(id);
        atualizaMensagensDoUsuario(usuario.getId(), usuario.getSenhaCriptografada(), senhaCriptografada);
        usuario.setSenhaCriptografada(senhaCriptografada);
        usuario.setToken(UUID.randomUUID());
        usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuarioPeloId(id);
        usuarioRepository.delete(usuario);
    }
}
