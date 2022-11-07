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
@Deprecated
@Service
@Getter
@Setter
public class CifraDeCesarServiceV1 {

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

    public CifraDeCesarDTO decodificarCifraDeCesar(String mensagem, int senha) {
        if(senha > 99 || senha < 1) {
            throw new RuntimeException("A senha deve estar entre 0 e 99!");
        }
        StringBuilder texto = new StringBuilder();
        int tamanhoTexto = mensagem.length();
        for (int c = 0; c < tamanhoTexto; c++) {
            int letraDecifradaASCII = ((int) mensagem.charAt(c)) - senha;
            while (letraDecifradaASCII < 32) {
                letraDecifradaASCII += 94;
            }
            texto.append((char) letraDecifradaASCII);
        }
        CifraDeCesarDTO cifraDeCesarDTO = new CifraDeCesarDTO();
        cifraDeCesarDTO.setSenha(senha);
        cifraDeCesarDTO.setMensagem(texto.toString());
        return cifraDeCesarDTO;
    }

    public CifraDeCesarDTO decodificarCifraDeCesarPersistida(Long id, int senha) {
        CifraDeCesar cifraDeCesar = cifraDeCesarRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado!"));
        CifraDeCesarDTO cifraDeCesarDTO = decodificarCifraDeCesar(cifraDeCesar.getMensagem(), senha);
        cifraDeCesarDTO.setId(cifraDeCesar.getId());
        return cifraDeCesarDTO;
    }

    public List<CifraDeCesarDTO> buscarTodas() {
        return CifraDeCesarMapper.toCifraDeCesarDTOList(cifraDeCesarRepository.findAll());
    }

    public CifraDeCesarDTO buscarCifra(Long id, int senha) {
        return decodificarCifraDeCesarPersistida(id, senha);
    }
}
