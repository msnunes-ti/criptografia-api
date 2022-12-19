package com.example.criptografiaapi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.criptografiaapi.config.JWTUtil;
import com.example.criptografiaapi.dtos.AtualizarUsuarioDTO;
import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.dtos.TokenDTO;
import com.example.criptografiaapi.dtos.UsuarioSensivelDTO;
import com.example.criptografiaapi.exceptions.CriptografiaApiException;
import com.example.criptografiaapi.mappers.UsuarioMapper;
import com.example.criptografiaapi.models.CifraDeCesarModel;
import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.repositories.CifraDeCesarRepository;
import com.example.criptografiaapi.repositories.UsuarioRepository;
import com.example.criptografiaapi.security.JWTAutenticarFilter;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTUtil jwtUtil;

    public Optional<Usuario> buscarUsuarioPeloUsuario(String usuario) {
        return Optional.ofNullable(usuarioRepository.findByUsername(usuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado!")));
    }

    public Usuario buscarUsuarioPeloId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public List<UsuarioSensivelDTO> buscarTodosUsuarios() {
        return  UsuarioMapper.toUsuarioSensivelDTOList(usuarioRepository.findAll());
    }

    public Boolean verificarSeExisteUsuario(String usuario) {
        Optional<Usuario> usuarioEncontrado = buscarUsuarioPeloUsuario(usuario);
        return usuarioEncontrado.isPresent();
    }

    public TokenDTO fazerLogin(String username, String password) {
        Usuario usuarioBuscado = buscarUsuarioPeloUsuario(username).orElseThrow(CriptografiaApiException::new);
        boolean valid = passwordEncoder.matches(password, usuarioBuscado.getPassword());
        if(!valid) {
            throw new CriptografiaApiException();
        }
        String token = jwtUtil.generateToken(usuarioBuscado);

//        String token = JWT.create().withSubject(usuarioBuscado.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + JWTAutenticarFilter.TOKEN_EXPIRACAO))
//                .sign(Algorithm.HMAC512(password));
        return TokenDTO.builder().token(token).build();
    }

    public void criarUsuario(@NotNull CriarUsuarioDTO criarUsuarioDTO) {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByUsername(criarUsuarioDTO.getUsername());
        if (usuarioBuscado.isPresent()) {
            throw new RuntimeException("Usuário já cadastrado.");
        }
        Usuario usuario = UsuarioMapper.toUsuario(criarUsuarioDTO);
        usuario.setIsAtivo(true);
        usuario.setPassword(passwordEncoder.encode(criarUsuarioDTO.getPassword()));
        usuario.setToken(UUID.randomUUID());
        usuarioRepository.save(usuario);
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
        Usuario usuario = buscarUsuarioPeloId(id);
        atualizaMensagensDoUsuario(usuario.getId(), usuario.getSenhaCriptografada(), atualizarUsuarioDTO.getSenhaCriptografada());
        usuario.setUsername(atualizarUsuarioDTO.getUsername());
        usuario.setNome(atualizarUsuarioDTO.getNome());
        usuario.setPassword(atualizarUsuarioDTO.getPassword());
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
