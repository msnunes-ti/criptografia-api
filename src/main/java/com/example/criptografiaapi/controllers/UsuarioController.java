package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.*;
import com.example.criptografiaapi.exceptions.CriptografiaApiException;
import com.example.criptografiaapi.mappers.UsuarioMapper;
import com.example.criptografiaapi.services.UsuarioService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ROLE_CONVIDADO', 'ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping(path = "/login")
    public TokenDTO fazerLogin(FazerLoginDTO fazerLoginDTO) {
        return usuarioService.fazerLogin(fazerLoginDTO.getUsername(), fazerLoginDTO.getPassword());
    }

    @PreAuthorize("hasAnyRole('ROLE_CONVIDADO', 'ROLE_USER', 'ROLE_ADMIN')")
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

    @PreAuthorize("hasAnyRole('ROLE_CONVIDADO', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public List<UsuarioSensivelDTO> usuarioSensivelDTOList() {
        return usuarioService.buscarTodosUsuarios();
    }

    @PreAuthorize("hasAnyRole('ROLE_CONVIDADO', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(path = "/{id}")
    public UsuarioSensivelDTO buscarUsuarioPeloId(@PathVariable Long id) {
        return UsuarioMapper.toUsuarioSensivelDTO(usuarioService.buscarUsuarioPeloId(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_CONVIDADO', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(path = "/existe/{usuario}")
    public ResponseEntity<Object> verificarSeExisteUsuario(@PathVariable String usuario) {
        Boolean usuarioEncontrado = usuarioService.verificarSeExisteUsuario(usuario);
        if (usuarioEncontrado) {
            return new ResponseEntity<>(HttpStatus.resolve(200));
        }
        return new ResponseEntity<>(HttpStatus.resolve(204));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping
    public void criarUsuario(@RequestBody @NotNull CriarUsuarioDTO criarUsuarioDTO) {
        usuarioService.criarUsuario(criarUsuarioDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping(path = "/{id}")
    public void atualizarUsuarioCompleto(@PathVariable @NotNull long id, @RequestBody @NotNull AtualizarUsuarioDTO atualizarUsuarioDTO) {
        usuarioService.atualizarUsuarioCompleto(id, atualizarUsuarioDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PatchMapping(path = "/{id}")
    public void atualizarSenhaCriptografadaDoUsuario(@PathVariable Long id, @RequestBody @NotNull AtualizarSenhaCriptografadaUsuarioDTO atualizarSenhaCriptografadaUsuarioDTO) {
        usuarioService.atualizarSenhaCriptografadaDoUsuario(id, atualizarSenhaCriptografadaUsuarioDTO.getSenhaCriptografada());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }
}
