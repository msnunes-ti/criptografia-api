package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.AtualizarSenhaCriptografadaUsuarioDTO;
import com.example.criptografiaapi.dtos.AtualizarUsuarioDTO;
import com.example.criptografiaapi.dtos.CriarUsuarioDTO;
import com.example.criptografiaapi.dtos.UsuarioSensivelDTO;
import com.example.criptografiaapi.mappers.UsuarioMapper;
import com.example.criptografiaapi.models.UsuarioModel;
import com.example.criptografiaapi.services.UsuarioService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(path = "/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String usuario, @RequestParam String senha){
        Optional<UsuarioModel> usuarioBuscado = usuarioService.buscarUsuarioPeloUsuario(usuario);
        if(usuarioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        UsuarioModel usuarioModelEncontrado = usuarioBuscado.get();
        Boolean valid = bCryptPasswordEncoder.matches(senha, usuarioModelEncontrado.getSenha());
        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }

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

    @PatchMapping(path = "/{id}")
    public void atualizarSenhaCriptografadaDoUsuario(@PathVariable Long id, @RequestBody @NotNull AtualizarSenhaCriptografadaUsuarioDTO atualizarSenhaCriptografadaUsuarioDTO) {
        usuarioService.atualizarSenhaCriptografadaDoUsuario(id, atualizarSenhaCriptografadaUsuarioDTO.getSenhaCriptografada());
    }

    @DeleteMapping(path = "/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }
}
