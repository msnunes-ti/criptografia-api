package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.dtos.DecodificarCifraDeCesarDTO;
import com.example.criptografiaapi.services.CifraDeCesarServiceV2;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v2/cifra")
public class CifraDeCesarControllerV2 {

    @Autowired
    CifraDeCesarServiceV2 cifraDeCesarServiceV2;

    @PostMapping(path = "/codificar")
    public @ResponseBody void codificarCifraDeCesar(@RequestBody @NotNull CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        cifraDeCesarServiceV2.criptografar(codificarCifraDeCesarDTO);
    }

    @PostMapping(path = "/decodificar")
    public @ResponseBody CifraDeCesarDTO decofificarCifraDeCesar(@RequestBody @NotNull DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO) {
        return cifraDeCesarServiceV2.decodificarCifraDeCesar(decodificarCifraDeCesarDTO);
    }

}

