package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.AtualizarUsuarioDTO;
import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioSensivelDTO;
import com.example.criptografiaapi.services.UsuarioService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping(path = "/{usuario}")
    public ResponseEntity<Object> buscarUsuario(@PathVariable String usuario) {
        return usuarioService.buscarPorUsuario(usuario);
    }

    @GetMapping
    public List<UsuarioSensivelDTO> usuarioSensivelDTOList() {
        return usuarioService.buscarTodosUsuarios();
    }

    @PostMapping
    public void criarUsuario(@RequestBody @NotNull CriarUsuarioDTO criarUsuarioDTO) {
        usuarioService.criarUsuario(criarUsuarioDTO);
    }

    @PutMapping(path = "/{id}")
    public void atualizarUsuario(@PathVariable @NotNull long id, @RequestBody @NotNull AtualizarUsuarioDTO atualizarUsuarioDTO) {
        usuarioService.atualizarUsuario(id, atualizarUsuarioDTO);
    }

}
