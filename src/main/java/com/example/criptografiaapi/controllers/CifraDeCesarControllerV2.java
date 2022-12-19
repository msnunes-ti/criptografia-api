package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.services.CifraDeCesarServiceV2;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v2/cifras")
public class CifraDeCesarControllerV2 {

    @Autowired
    CifraDeCesarServiceV2 cifraDeCesarServiceV2;

    @PreAuthorize("hasAnyRole('ROLE_CONVIDADO', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public List<CifraDeCesarDTO> buscarTodasAsCifrasAindaCodificadas() {
        return cifraDeCesarServiceV2.buscarTodasAsCifrasAindaCodificadas();
    }

    @PreAuthorize("hasAnyRole('ROLE_CONVIDADO', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(path = "/usuario/{id}")
    public List<CifraDeCesarDTO> buscarTodasAsCifrasDoUsuarioJaDecodificadas(@PathVariable Long id) {
        return cifraDeCesarServiceV2.buscarTodasAsCifrasDoUsuarioJaDecodificadas(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_CONVIDADO', 'ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(path = "/{id}")
    public CifraDeCesarDTO buscarCifraPorIdJaDecodificada(@PathVariable Long id) {
        return cifraDeCesarServiceV2.buscarDecodificada(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping(path = "/codificar")
    public @ResponseBody CifraDeCesarDTO codificarCifraDeCesar(@RequestBody @NotNull CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        return cifraDeCesarServiceV2.criptografarDepoisPersistir(codificarCifraDeCesarDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/{id}")
    public void deletarCifraDeCesar(@PathVariable Long id){
        cifraDeCesarServiceV2.deletarCifraDeCesar(id);
    }
}

