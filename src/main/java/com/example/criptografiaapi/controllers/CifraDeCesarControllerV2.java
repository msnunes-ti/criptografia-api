package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CriarCifraDeCesarDTO;
import com.example.criptografiaapi.services.CifraDeCesarServiceV2;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v2/cifras")
public class CifraDeCesarControllerV2 {

    @Autowired
    CifraDeCesarServiceV2 cifraDeCesarServiceV2;

    @GetMapping
    public List<CifraDeCesarDTO> buscarTodasAsCifrasAindaCodificadas() {
        return cifraDeCesarServiceV2.buscarTodasAsCifrasAindaCodificadas();
    }

    @GetMapping(path = "/usuario")
    public List<CifraDeCesarDTO> buscarTodasAsCifrasDoUsuarioJaDecodificadas() {
        return cifraDeCesarServiceV2.buscarTodasAsCifrasDoUsuarioJaDecodificadas();
    }

    @GetMapping(path = "/{id}")
    public CifraDeCesarDTO buscarCifraPorIdJaDecodificada(@PathVariable Long id) {
        return cifraDeCesarServiceV2.buscarDecodificada(id);
    }

    @PostMapping(path = "/codificar")
    public @ResponseBody CifraDeCesarDTO codificarCifraDeCesar(@RequestBody @NotNull CriarCifraDeCesarDTO criarCifraDeCesarDTO) {
        return cifraDeCesarServiceV2.criptografarDepoisPersistir(criarCifraDeCesarDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void deletarCifraDeCesar(@PathVariable Long id){
        cifraDeCesarServiceV2.deletarCifraDeCesar(id);
    }
}

