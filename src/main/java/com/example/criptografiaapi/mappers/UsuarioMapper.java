package com.example.criptografiaapi.mappers;

import com.example.criptografiaapi.dtos.*;
import com.example.criptografiaapi.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioMapper {

    public static Usuario toUsuario(CriarUsuarioDTO criarUsuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(criarUsuarioDTO.getUsername());
        usuario.setNome(criarUsuarioDTO.getNome());
        usuario.setPassword(criarUsuarioDTO.getPassword());
        usuario.setSenhaCriptografada(criarUsuarioDTO.getSenhaCriptografada());
        usuario.setEmail(criarUsuarioDTO.getEmail());
        return usuario;
    }

    public static Usuario toUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(atualizarUsuarioDTO.getNome());
        usuario.setPassword(atualizarUsuarioDTO.getPassword());
        usuario.setSenhaCriptografada(atualizarUsuarioDTO.getSenhaCriptografada());
        usuario.setEmail(atualizarUsuarioDTO.getEmail());
        return usuario;
    }

    public static UsuarioDTO toUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setSenhaCriptografada(usuario.getSenhaCriptografada());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setToken(usuario.getToken());
        usuarioDTO.setIsAtivo(usuario.getIsAtivo());
        return  usuarioDTO;
    }

    public static List<UsuarioSensivelDTO> toUsuarioSensivelDTOList(List<Usuario> usuarioList) {
        List<UsuarioSensivelDTO> usuarioSensivelDTOList = new ArrayList<>();
        for(Usuario u: usuarioList) {
            UsuarioSensivelDTO usuarioSensivelDTO = new UsuarioSensivelDTO();
            usuarioSensivelDTO.setId(u.getId());
            usuarioSensivelDTO.setUsername(u.getUsername());
            usuarioSensivelDTO.setNome(u.getNome());
            usuarioSensivelDTO.setEmail(u.getEmail());
            usuarioSensivelDTO.setIsAtivo(u.getIsAtivo());
            usuarioSensivelDTOList.add(usuarioSensivelDTO);
        }
        return usuarioSensivelDTOList;
    }
    public static UsuarioSensivelDTO toUsuarioSensivelDTO(Usuario usuario) {
        UsuarioSensivelDTO usuarioSensivelDTO = new UsuarioSensivelDTO();
        usuarioSensivelDTO.setId(usuario.getId());
        usuarioSensivelDTO.setUsername(usuario.getUsername());
        usuarioSensivelDTO.setNome(usuario.getNome());
        usuarioSensivelDTO.setEmail(usuario.getEmail());
        usuarioSensivelDTO.setIsAtivo(usuario.getIsAtivo());
        return usuarioSensivelDTO;
    }
}
