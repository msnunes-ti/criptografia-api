package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.*;
import com.example.criptografiaapi.mappers.UsuarioMapper;
import com.example.criptografiaapi.models.CifraDeCesarModel;
import com.example.criptografiaapi.models.UsuarioModel;
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

    public Optional<UsuarioModel> buscarUsuarioPeloUsuario(String usuario) {
        return Optional.ofNullable(usuarioRepository.findByUsuario(usuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado!")));
    }

    public UsuarioModel buscarUsuarioPeloId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public List<UsuarioSensivelDTO> buscarTodosUsuarios() {
        return  UsuarioMapper.toUsuarioSensivelDTOList(usuarioRepository.findAll());
    }

    public Boolean verificarSeExisteUsuario(String usuario) {
        Optional<UsuarioModel> usuarioEncontrado = buscarUsuarioPeloUsuario(usuario);
        return usuarioEncontrado.isPresent();
    }

    @Transactional
    public void criarUsuario(@NotNull CriarUsuarioDTO criarUsuarioDTO) {
        Optional<UsuarioModel> buscarUsuario = usuarioRepository.findByUsuario(criarUsuarioDTO.getUsuario());
        if (buscarUsuario.isPresent()) {
            throw new RuntimeException("Usuário já cadastrado.");
        }
        UsuarioModel usuarioModel = UsuarioMapper.toUsuario(criarUsuarioDTO);
        usuarioModel.setIsAtivo(true);
        usuarioModel.setSenha(bCryptPasswordEncoder.encode(criarUsuarioDTO.getSenha()));
        usuarioModel.setToken(UUID.randomUUID());
        usuarioRepository.save(usuarioModel);
    }

    private void atualizaMensagensDoUsuario(Long id, Integer senhaCriptografadaAntiga, Integer senhaCriptografadaNova) {
        List<CifraDeCesarModel> cifraDeCesarModelList = cifraDeCesarRepository.findAllByUsuarioId(id);
        for (CifraDeCesarModel c: cifraDeCesarModelList) {
            String mensagemDecodificada = cifraDeCesarServiceV2.decodificarMensagem(c.getMensagem(), senhaCriptografadaAntiga);
            String mensagemRecodificada = cifraDeCesarServiceV2.criptografarMensagem(mensagemDecodificada, senhaCriptografadaNova);
            c.setMensagem(mensagemRecodificada);
            cifraDeCesarRepository.save(c);
        }
    }

    public void atualizarUsuarioCompleto(Long id, AtualizarUsuarioDTO atualizarUsuarioDTO) {
        UsuarioModel usuarioModel = buscarUsuarioPeloId(id);
        atualizaMensagensDoUsuario(usuarioModel.getId(), usuarioModel.getSenhaCriptografada(), atualizarUsuarioDTO.getSenhaCriptografada());
        usuarioModel.setUsuario(atualizarUsuarioDTO.getUsuario());
        usuarioModel.setNome(atualizarUsuarioDTO.getNome());
        usuarioModel.setSenha(atualizarUsuarioDTO.getSenha());
        usuarioModel.setEmail(atualizarUsuarioDTO.getEmail());
        usuarioModel.setSenhaCriptografada(atualizarUsuarioDTO.getSenhaCriptografada());
        usuarioModel.setIsAtivo(atualizarUsuarioDTO.getIsAtivo());
        usuarioRepository.save(usuarioModel);
    }

    public void atualizarSenhaCriptografadaDoUsuario(Long id, Integer senhaCriptografada) {
        UsuarioModel usuarioModel = buscarUsuarioPeloId(id);
        atualizaMensagensDoUsuario(usuarioModel.getId(), usuarioModel.getSenhaCriptografada(), senhaCriptografada);
        usuarioModel.setSenhaCriptografada(senhaCriptografada);
        usuarioModel.setToken(UUID.randomUUID());
        usuarioRepository.save(usuarioModel);
    }

    public void deletarUsuario(Long id) {
        UsuarioModel usuarioModel = buscarUsuarioPeloId(id);
        usuarioRepository.delete(usuarioModel);
    }
}
