package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.*;
import com.example.criptografiaapi.exceptions.CriptografiaApiException;
import com.example.criptografiaapi.mappers.UsuarioMapper;
import com.example.criptografiaapi.services.UsuarioService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(path = "/login")
    public TokenDTO fazerLogin(@RequestBody FazerLoginDTO fazerLoginDTO) {
        return usuarioService.fazerLogin(fazerLoginDTO.getUsername(), fazerLoginDTO.getPassword());
    }

    @GetMapping(path = "/validar-senha")
    public ResponseEntity<TokenDTO> validarSenha(@RequestParam String username, @RequestParam String password){
        try {
            TokenDTO tokenDTO = usuarioService.fazerLogin(username, password);
            return ResponseEntity.ok(tokenDTO);
        }
        catch (CriptografiaApiException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping
    public List<UsuarioSensivelDTO> usuarioSensivelDTOList() {
        return usuarioService.buscarTodosUsuarios();
    }

    @GetMapping(path = "/{id}")
    public UsuarioSensivelDTO buscarUsuarioPeloId(@PathVariable Long id) {
        return UsuarioMapper.toUsuarioSensivelDTO(usuarioService.buscarUsuarioPeloId(id));
    }

    @GetMapping(path = "/existe/{usuario}")
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

    @PatchMapping(path = "/{id}")
    public void atualizarSenhaCriptografadaDoUsuario(@PathVariable Long id, @RequestBody @NotNull AtualizarSenhaCriptografadaUsuarioDTO atualizarSenhaCriptografadaUsuarioDTO) {
        usuarioService.atualizarSenhaCriptografadaDoUsuario(id, atualizarSenhaCriptografadaUsuarioDTO.getSenhaCriptografada());
    }

    @DeleteMapping(path = "/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }
}
