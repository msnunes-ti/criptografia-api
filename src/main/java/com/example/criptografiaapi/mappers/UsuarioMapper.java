package com.example.criptografiaapi.mappers;

import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.models.Usuario;

public class UsuarioMapper {

    public Usuario toUsuario (CriarUsuarioDTO criarUsuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsuario(criarUsuarioDTO.getUsuario());
        usuario.setNome(criarUsuarioDTO.getNome());
        usuario.setSenha(criarUsuarioDTO.getSenha());
        usuario.setSenhaCriptografada(criarUsuarioDTO.getSenhaCriptografada());
        usuario.setEmail(criarUsuarioDTO.getEmail());
        return usuario;
    }

}
