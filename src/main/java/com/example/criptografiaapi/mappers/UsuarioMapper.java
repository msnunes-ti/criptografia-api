package com.example.criptografiaapi.mappers;

import com.example.criptografiaapi.dtos.AtualizarUsuarioDTO;
import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioSensivelDTO;
import com.example.criptografiaapi.models.UsuarioModel;

import java.util.ArrayList;
import java.util.List;

public class UsuarioMapper {

    public static UsuarioModel toUsuario(CriarUsuarioDTO criarUsuarioDTO) {
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setUsuario(criarUsuarioDTO.getUsuario());
        usuarioModel.setNome(criarUsuarioDTO.getNome());
        usuarioModel.setSenha(criarUsuarioDTO.getSenha());
        usuarioModel.setSenhaCriptografada(criarUsuarioDTO.getSenhaCriptografada());
        usuarioModel.setEmail(criarUsuarioDTO.getEmail());
        return usuarioModel;
    }

    public static UsuarioModel toUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO) {
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setNome(atualizarUsuarioDTO.getNome());
        usuarioModel.setSenha(atualizarUsuarioDTO.getSenha());
        usuarioModel.setSenhaCriptografada(atualizarUsuarioDTO.getSenhaCriptografada());
        usuarioModel.setEmail(atualizarUsuarioDTO.getEmail());
        return usuarioModel;
    }

    public static UsuarioDTO toUsuarioDTO(UsuarioModel usuarioModel) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuarioModel.getId());
        usuarioDTO.setUsuario(usuarioModel.getUsuario());
        usuarioDTO.setNome(usuarioModel.getNome());
        usuarioDTO.setSenha(usuarioModel.getSenha());
        usuarioDTO.setSenhaCriptografada(usuarioModel.getSenhaCriptografada());
        usuarioDTO.setEmail(usuarioModel.getEmail());
        usuarioDTO.setToken(usuarioModel.getToken());
        usuarioDTO.setIsAtivo(usuarioModel.getIsAtivo());
        return  usuarioDTO;
    }

    public static List<UsuarioSensivelDTO> toUsuarioSensivelDTOList(List<UsuarioModel> usuarioModelList) {
        List<UsuarioSensivelDTO> usuarioSensivelDTOList = new ArrayList<>();
        for(UsuarioModel u: usuarioModelList) {
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
    public static UsuarioSensivelDTO toUsuarioSensivelDTO(UsuarioModel usuarioModel) {
            UsuarioSensivelDTO usuarioSensivelDTO = new UsuarioSensivelDTO();
            usuarioSensivelDTO.setId(usuarioModel.getId());
            usuarioSensivelDTO.setUsuario(usuarioModel.getUsuario());
            usuarioSensivelDTO.setNome(usuarioModel.getNome());
            usuarioSensivelDTO.setEmail(usuarioModel.getEmail());
            usuarioSensivelDTO.setIsAtivo(usuarioModel.getIsAtivo());
        return usuarioSensivelDTO;
    }
}
