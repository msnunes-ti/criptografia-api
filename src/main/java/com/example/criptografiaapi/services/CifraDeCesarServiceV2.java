package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CifraDeCesarServiceV2 {

    public CifraDeCesarDTO criptografar(CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        if(codificarCifraDeCesarDTO.getSenha() > 99 || codificarCifraDeCesarDTO.getSenha() < 1) {
            throw new RuntimeException("A senha deve estar entre 0 e 99");
        }
        List<String> letras = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        int inteiro = (int) codificarCifraDeCesarDTO.getSenha() % letras.size();
        int codigoValido = ((codificarCifraDeCesarDTO.getSenha() % letras.size()) - inteiro) * letras.size();
        char[] cifrar = codificarCifraDeCesarDTO.getMensagem().toCharArray();
        StringBuilder cifrado = new StringBuilder();
        for (int i = 0; i < cifrar.length; i++) {
            char novo = cifrar[i];


        }

        return null;
    }
}
