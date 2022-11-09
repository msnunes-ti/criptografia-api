package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.services.CifraDeCesarServiceV2;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v2/cifra")
public class CifraDeCesarControllerV2 {

    @Autowired
    CifraDeCesarServiceV2 cifraDeCesarService;

    @PostMapping(path = "/codificar")
    public @ResponseBody void codificarCifraDeCesar(@RequestBody @NotNull CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        cifraDeCesarService.criptografar(codificarCifraDeCesarDTO);
    }

}

