package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.models.CifraDeCesar;
import com.example.criptografiaapi.repositories.CifraDeCesarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CifraDeCesarServiceV2 {

    @Autowired
    CifraDeCesarRepository cifraDeCesarRepository;

    public void criptografar(CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        if(codificarCifraDeCesarDTO.getSenha() > 99 || codificarCifraDeCesarDTO.getSenha() < 1) {
            throw new RuntimeException("A senha deve estar entre 0 e 99");
        }
        List<String> letras = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        int parteInteira = codificarCifraDeCesarDTO.getSenha() % letras.size();
//        int codigoValido = (((codificarCifraDeCesarDTO.getSenha() % letras.size()) * letras.size()) - parteInteira);
        StringBuilder mensagemCodificada = new StringBuilder();
        for (int i = 0; i < codificarCifraDeCesarDTO.getMensagem().length(); i++) {
            int indice = 0;
            String letraParaCifrar = String.valueOf(codificarCifraDeCesarDTO.getMensagem().charAt(i));

            for (int j = 1; j < letras.size(); j++) {
                if (letras.get(j).equalsIgnoreCase(letraParaCifrar)) {
                    indice = j;
                    break;
                }
            }
            int letraCifrada = indice + parteInteira;

            while (letraCifrada > 25) {
                letraCifrada -= 26;

            }
//            indice = 0;
            if (letraParaCifrar.equals(" ")) {
                mensagemCodificada.append(" ");
            } else {
                mensagemCodificada.append(letras.get(letraCifrada));
            }
        }
        CifraDeCesar cifraDeCesar = new CifraDeCesar();
        cifraDeCesar.setMensagem(mensagemCodificada.toString());
        cifraDeCesar.setSenha(codificarCifraDeCesarDTO.getSenha());
        cifraDeCesarRepository.save(cifraDeCesar);
    }
}
