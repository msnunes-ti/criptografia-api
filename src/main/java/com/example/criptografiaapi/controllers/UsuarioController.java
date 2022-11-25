package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioDTO;
import com.example.criptografiaapi.mappers.UsuarioMapper;
import com.example.criptografiaapi.services.UsuarioService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public UsuarioDTO buscarUsuario(@RequestBody String usuario) {
        return UsuarioMapper.toUsuarioDTO(usuarioService.buscarPorUsuario(usuario));
    }

    @PostMapping
    public void criarUsuario(@NotNull @RequestBody CriarUsuarioDTO criarUsuarioDTO) {
        usuarioService.criarUsuario(criarUsuarioDTO);
    }

}
