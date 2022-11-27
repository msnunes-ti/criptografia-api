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
@RequestMapping(path = "/v2/cifras")
public class CifraDeCesarControllerV2 {

    @Autowired
    CifraDeCesarServiceV2 cifraDeCesarServiceV2;

    @GetMapping
    public List<CifraDeCesarDTO> buscarTodasAsCifrasCodificadas() {
        return cifraDeCesarServiceV2.buscarTodasCifradas();
    }

    @GetMapping(path = "/{id}")
    public CifraDeCesarDTO buscarCifraCodificada(@PathVariable Long id) {
        return cifraDeCesarServiceV2.buscarCifra(id);
    }

    @PostMapping(path = "/codificar")
    public @ResponseBody CifraDeCesarDTO codificarCifraDeCesar(@RequestBody @NotNull CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        return cifraDeCesarServiceV2.criptografar(codificarCifraDeCesarDTO);
    }

    @PostMapping(path = "/decodificar")
    public @ResponseBody CifraDeCesarDTO decofificarCifraDeCesar(@RequestBody @NotNull DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO) {
        return cifraDeCesarServiceV2.decodificarCifraDeCesar(decodificarCifraDeCesarDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void deletarCifraDeCesar(@PathVariable Long id){
        cifraDeCesarServiceV2.deletarCifraDeCesar(id);
    }
}

