package com.example.criptografiaapi.controllers;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
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
    public List<CifraDeCesarDTO> buscarTodas() {
        return cifraDeCesarService.buscarTodas();
    }

    @PostMapping
    public @ResponseBody void codificarCifraDeCesar(@RequestBody @NotNull CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        cifraDeCesarService.codificarCifraDeCesar(codificarCifraDeCesarDTO);
    }

}
