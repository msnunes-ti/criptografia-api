package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.dtos.DecodificarCifraDeCesarDTO;
import com.example.criptografiaapi.services.CifraDeCesarServiceV1;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Deprecated
@RestController
@RequestMapping(path = "/v1/cifra")
public class CifraDeCesarControllerV1 {

    @Autowired
    CifraDeCesarServiceV1 cifraDeCesarService;

    @GetMapping
    public List<CifraDeCesarDTO> buscarTodasCodificadas() {
        return cifraDeCesarService.buscarTodas();
    }

    @PostMapping(path = "/codificar")
    public @ResponseBody void codificarCifraDeCesar(@RequestBody @NotNull CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        cifraDeCesarService.codificarCifraDeCesar(codificarCifraDeCesarDTO);
    }

    @PostMapping(path = "/decodificar")
    public @ResponseBody CifraDeCesarDTO decodificarCifraDeCesar(@RequestBody @NotNull DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO) {
        return cifraDeCesarService.decodificarCifraDeCesar(decodificarCifraDeCesarDTO);
    }
}
