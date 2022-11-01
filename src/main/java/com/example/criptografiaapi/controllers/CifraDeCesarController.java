package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.dtos.DecodificarCifraDeCesarDTO;
import com.example.criptografiaapi.services.CifraDeCesarService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cifra")
public class CifraDeCesarController {

    @Autowired
    CifraDeCesarService cifraDeCesarService;

    @GetMapping
    public List<CifraDeCesarDTO> buscarTodasCodificadas() {
        return cifraDeCesarService.buscarTodas();
    }

    @GetMapping(path = "{id}")
    public CifraDeCesarDTO buscarCifra(@PathVariable Long id, @RequestBody CifraDeCesarDTO cifraDeCesarDTO) {
        return cifraDeCesarService.buscarCifra(id, cifraDeCesarDTO.getSenha());
    }

    @PostMapping(path = "/codificar")
    public @ResponseBody void codificarCifraDeCesar(@RequestBody @NotNull CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        cifraDeCesarService.codificarCifraDeCesar(codificarCifraDeCesarDTO);
    }

    @PostMapping(path = "/decodificar")
    public @ResponseBody CifraDeCesarDTO decodificarCifraDeCesar(@RequestBody @NotNull DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO) {
        return cifraDeCesarService.decodificarCifraDeCesar(decodificarCifraDeCesarDTO.getMensagem(), decodificarCifraDeCesarDTO.getSenha());
    }

}
