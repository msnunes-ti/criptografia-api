package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.AtualizarUsuarioDTO;
import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioSensivelDTO;
import com.example.criptografiaapi.mappers.UsuarioMapper;
import com.example.criptografiaapi.services.UsuarioService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioSensivelDTO> usuarioSensivelDTOList() {
        return usuarioService.buscarTodosUsuarios();
    }

    @GetMapping(path = "/{id}")
    public UsuarioSensivelDTO buscarUsuarioPeloId(@PathVariable Long id) {
        return UsuarioMapper.toUsuarioSensivelDTO(usuarioService.buscarUsuarioPeloId(id));
    }

    @GetMapping(path = "/usuario/{usuario}")
    public ResponseEntity<Object> verificarSeExisteUsuario(@PathVariable String usuario) {
        Boolean usuarioEncontrado = usuarioService.verificarSeExisteUsuario(usuario);
        if (usuarioEncontrado) {
            return new ResponseEntity<>(HttpStatus.resolve(200));
        }
        return new ResponseEntity<>(HttpStatus.resolve(204));
    }

    @PostMapping
    public void criarUsuario(@RequestBody @NotNull CriarUsuarioDTO criarUsuarioDTO) {
        usuarioService.criarUsuario(criarUsuarioDTO);
    }

    @PutMapping(path = "/{id}")
    public void atualizarUsuarioCompleto(@PathVariable @NotNull long id, @RequestBody @NotNull AtualizarUsuarioDTO atualizarUsuarioDTO) {
        usuarioService.atualizarUsuarioCompleto(id, atualizarUsuarioDTO);
    }

    @PutMapping(path = "/usuario/{id}")
    public void atualizarSenhaCriptografada(@PathVariable Long id, @RequestBody @NotNull AtualizaSenhaCriptografadaUsuarioDTO atualizaSenhaCriptografadaUsuarioDTO) {

    }

    @DeleteMapping(path = "/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }

}
