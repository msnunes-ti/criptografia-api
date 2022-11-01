package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.mappers.CifraDeCesarMapper;
import com.example.criptografiaapi.models.CifraDeCesar;
import com.example.criptografiaapi.repositories.CifraDeCesarRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class CifraDeCesarService {

    @Autowired
    CifraDeCesarRepository cifraDeCesarRepository;

    public void codificarCifraDeCesar(CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        if(codificarCifraDeCesarDTO.getSenha() > 99 || codificarCifraDeCesarDTO.getSenha() < 1) {
            throw new RuntimeException("A senha deve estar entre 0 e 99");
        }
        StringBuilder textoCifrado = new StringBuilder();
        int tamanhoTexto = codificarCifraDeCesarDTO.getMensagem().length();
        for (int c = 0; c < tamanhoTexto; c++) {
            int letraCifradaASCII = ((int) codificarCifraDeCesarDTO.getMensagem().charAt(c)) + codificarCifraDeCesarDTO.getSenha();
            while (letraCifradaASCII > 126) {
                letraCifradaASCII -= 94;
            }
            textoCifrado.append((char) letraCifradaASCII);
        }
        CifraDeCesar cifraDeCesar = new CifraDeCesar();
        cifraDeCesar.setMensagem(textoCifrado.toString());
        cifraDeCesar.setSenha(codificarCifraDeCesarDTO.getSenha());
        cifraDeCesarRepository.save(cifraDeCesar);
    }

    public String decodificarCifraDeCesar(Long id, int senha) {
        CifraDeCesar cifraDeCesar = cifraDeCesarRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado!"));

        if(senha > 99 || senha < 1) {
            throw new RuntimeException("A senha deve estar entre 0 e 99!");
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

    public List<CifraDeCesarDTO> buscarTodas() {
        return CifraDeCesarMapper.toCifraDeCesarDTOList(cifraDeCesarRepository.findAll());
    }
}
