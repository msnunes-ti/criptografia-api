package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CriarCifraDeCesarDTO;
import com.example.criptografiaapi.dtos.DecodificarCifraDeCesarDTO;
import com.example.criptografiaapi.mappers.CifraDeCesarMapper;
import com.example.criptografiaapi.models.CifraDeCesarModel;
import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.repositories.CifraDeCesarRepository;
import com.example.criptografiaapi.repositories.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Deprecated
@Service
@Getter
@Setter
public class CifraDeCesarServiceV1 {

    @Autowired
    CifraDeCesarRepository cifraDeCesarRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

//    public void codificarCifraDeCesar(CriarCifraDeCesarDTO criarCifraDeCesarDTO) {
//        Optional<Usuario> usuario = usuarioRepository.findById(criarCifraDeCesarDTO.getUsuarioId());
//        if(usuario.get().getSenhaCriptografada() > 99 || usuario.get().getSenhaCriptografada() < 1) {
//            throw new RuntimeException("A senha deve estar entre 0 e 99");
//        }
//        StringBuilder textoCifrado = new StringBuilder();
//        int tamanhoTexto = criarCifraDeCesarDTO.getMensagem().length();
//        for (int c = 0; c < tamanhoTexto; c++) {
//            int letraCifradaASCII = ((int) criarCifraDeCesarDTO.getMensagem().charAt(c)) + usuario.get().getSenhaCriptografada();
//            while (letraCifradaASCII > 126) {
//                letraCifradaASCII -= 94;
//            }
//            textoCifrado.append((char) letraCifradaASCII);
//        }
//        CifraDeCesarModel cifraDeCesarModel = new CifraDeCesarModel();
//        cifraDeCesarModel.setMensagem(textoCifrado.toString());
//        cifraDeCesarRepository.save(cifraDeCesarModel);
//    }

    public CifraDeCesarDTO decodificarCifraDeCesar(DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO) {
        Optional<Usuario> usuario = usuarioRepository.findById(decodificarCifraDeCesarDTO.getUsuarioId());
        if(usuario.get().getSenhaCriptografada() > 99 || usuario.get().getSenhaCriptografada() < 1) {
            throw new RuntimeException("A senha deve estar entre 0 e 99!");
        }
        StringBuilder texto = new StringBuilder();
        int tamanhoTexto = decodificarCifraDeCesarDTO.getMensagem().length();
        for (int c = 0; c < tamanhoTexto; c++) {
            int letraDecifradaASCII = ((int) decodificarCifraDeCesarDTO.getMensagem().charAt(c)) - usuario.get().getSenhaCriptografada();
            while (letraDecifradaASCII < 32) {
                letraDecifradaASCII += 94;
            }
            texto.append((char) letraDecifradaASCII);
        }
        CifraDeCesarDTO cifraDeCesarDTO = new CifraDeCesarDTO();
        cifraDeCesarDTO.setMensagem(texto.toString());
        return cifraDeCesarDTO;
    }

    public List<CifraDeCesarDTO> buscarTodas() {
        return CifraDeCesarMapper.toCifraDeCesarDTOList(cifraDeCesarRepository.findAll());
    }
}
