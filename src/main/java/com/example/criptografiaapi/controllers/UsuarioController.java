package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.AtualizarUsuarioDTO;
import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioDTO;
import com.example.criptografiaapi.services.UsuarioService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Object> buscarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.buscarPorUsuario(usuarioDTO.getUsuario());
    }

    @PostMapping
    public void criarUsuario(@NotNull @RequestBody CriarUsuarioDTO criarUsuarioDTO) {
        usuarioService.criarUsuario(criarUsuarioDTO);
    }

    @PutMapping(path = "/{id}")
    public void atualizarUsuario(@PathVariable long id, @RequestBody AtualizarUsuarioDTO atualizarUsuarioDTO) {
        usuarioService.atualizarUsuario(id, atualizarUsuarioDTO);
    }

}
