package com.example.criptografiaapi.services;

import com.example.criptografiaapi.models.CifraDeCesar;
import com.example.criptografiaapi.repositories.CifraDeCesarRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CifraDeCesarService {

    @Autowired
    CifraDeCesarRepository cifraDeCesarRepository;

    public String codificarCifraDeCesar(int senha, String texto) {
        if(senha > 99 || senha < 1) {
            throw new RuntimeException("A senha deve estar entre 0 e 99");
        }
        StringBuilder textoCifrado = new StringBuilder();
        int tamanhoTexto = texto.length();
        for (int c = 0; c < tamanhoTexto; c++) {
            int letraCifradaASCII = ((int) texto.charAt(c)) + senha;
            while (letraCifradaASCII > 126) {
                letraCifradaASCII -= 94;
            }
            textoCifrado.append((char) letraCifradaASCII);
        }
        return textoCifrado.toString();
    }

    public String descodificarCifraDeCesar(Long id, int senha) {
        CifraDeCesar cifraDeCesar = cifraDeCesarRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado!"));

        if(senha > 99 || senha < 1) {
            throw new RuntimeException("A senha deve estar entre 0 e 99");
        }
        StringBuilder texto = new StringBuilder();
        int tamanhoTexto = cifraDeCesar.getMensagem().length();
        for (int c = 0; c < tamanhoTexto; c++) {
            int letraDecifradaASCII = ((int) cifraDeCesar.getMensagem().charAt(c)) - senha;

            while (letraDecifradaASCII < 32) {
                letraDecifradaASCII += 94;
            }
            texto.append((char) letraDecifradaASCII);
        }
        return texto.toString();
    }
}
