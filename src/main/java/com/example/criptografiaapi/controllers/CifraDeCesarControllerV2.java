package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.dtos.DecodificarCifraDeCesarDTO;
import com.example.criptografiaapi.services.CifraDeCesarServiceV2;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v2/cifra")
public class CifraDeCesarControllerV2 {

    @Autowired
    CifraDeCesarServiceV2 cifraDeCesarServiceV2;

    @GetMapping
    public List<CifraDeCesarDTO> buscarTodasCodificadas() {
        return cifraDeCesarServiceV2.buscarTodas();
    }

    @GetMapping(path = "/{id}")
    public CifraDeCesarDTO buscarCifra(@PathVariable Long id, @RequestBody CifraDeCesarDTO cifraDeCesarDTO) {
        return cifraDeCesarServiceV2.buscarCifra(id, cifraDeCesarDTO.getSenha());
    }

    @PostMapping(path = "/codificar")
    public @ResponseBody CifraDeCesarDTO codificarCifraDeCesar(@RequestBody @NotNull CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        return cifraDeCesarServiceV2.criptografar(codificarCifraDeCesarDTO);
    }

    @PostMapping(path = "/decodificar")
    public @ResponseBody CifraDeCesarDTO decofificarCifraDeCesar(@RequestBody @NotNull DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO) {
        return cifraDeCesarServiceV2.decodificarCifraDeCesar(decodificarCifraDeCesarDTO);
    }
}

