package com.example.criptografiaapi.mappers;

import com.example.criptografiaapi.dtos.AtualizarUsuarioDTO;
import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioSensivelDTO;
import com.example.criptografiaapi.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioMapper {

    public static Usuario toUsuario(CriarUsuarioDTO criarUsuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsuario(criarUsuarioDTO.getUsuario());
        usuario.setNome(criarUsuarioDTO.getNome());
        usuario.setSenha(criarUsuarioDTO.getSenha());
        usuario.setSenhaCriptografada(criarUsuarioDTO.getSenhaCriptografada());
        usuario.setEmail(criarUsuarioDTO.getEmail());
        return usuario;
    }

    public static Usuario toUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(atualizarUsuarioDTO.getNome());
        usuario.setSenha(atualizarUsuarioDTO.getSenha());
        usuario.setSenhaCriptografada(atualizarUsuarioDTO.getSenhaCriptografada());
        usuario.setEmail(atualizarUsuarioDTO.getEmail());
        return usuario;
    }

    public static UsuarioDTO toUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUsuario(usuario.getUsuario());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setSenha(usuario.getSenha());
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
            usuarioSensivelDTO.setUsuario(u.getUsuario());
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
            usuarioSensivelDTO.setUsuario(usuario.getUsuario());
            usuarioSensivelDTO.setNome(usuario.getNome());
            usuarioSensivelDTO.setEmail(usuario.getEmail());
            usuarioSensivelDTO.setIsAtivo(usuario.getIsAtivo());
        return usuarioSensivelDTO;
    }
}
