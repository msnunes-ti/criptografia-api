package com.example.criptografiaapi.mappers;

import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioDTO;
import com.example.criptografiaapi.models.Usuario;

public class UsuarioMapper {

    public static Usuario toUsuario (CriarUsuarioDTO criarUsuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsuario(criarUsuarioDTO.getUsuario());
        usuario.setNome(criarUsuarioDTO.getNome());
        usuario.setSenha(criarUsuarioDTO.getSenha());
        usuario.setSenhaCriptografada(criarUsuarioDTO.getSenhaCriptografada());
        usuario.setEmail(criarUsuarioDTO.getEmail());
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
        usuarioDTO.setIsAtivo(usuario.getIsAtivo());
        return  usuarioDTO;
    }

}
