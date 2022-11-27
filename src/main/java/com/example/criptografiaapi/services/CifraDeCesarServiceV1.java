package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.dtos.DecodificarCifraDeCesarDTO;
import com.example.criptografiaapi.mappers.CifraDeCesarMapper;
import com.example.criptografiaapi.models.CifraDeCesar;
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

    public void codificarCifraDeCesar(CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        Optional<Usuario> usuario = usuarioRepository.findById(codificarCifraDeCesarDTO.getUsuarioId());
        if(usuario.get().getSenhaCriptografada() > 99 || usuario.get().getSenhaCriptografada() < 1) {
            throw new RuntimeException("A senha deve estar entre 0 e 99");
        }
        StringBuilder textoCifrado = new StringBuilder();
        int tamanhoTexto = codificarCifraDeCesarDTO.getMensagem().length();
        for (int c = 0; c < tamanhoTexto; c++) {
            int letraCifradaASCII = ((int) codificarCifraDeCesarDTO.getMensagem().charAt(c)) + usuario.get().getSenhaCriptografada();
            while (letraCifradaASCII > 126) {
                letraCifradaASCII -= 94;
            }
            textoCifrado.append((char) letraCifradaASCII);
        }
        CifraDeCesar cifraDeCesar = new CifraDeCesar();
        cifraDeCesar.setMensagem(textoCifrado.toString());
//        cifraDeCesar.setSenha(codificarCifraDeCesarDTO.getSenhaCriptografada());
        cifraDeCesarRepository.save(cifraDeCesar);
    }

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
//        cifraDeCesarDTO.setSenha(senha);
        cifraDeCesarDTO.setMensagem(texto.toString());
        return cifraDeCesarDTO;
    }

//    public CifraDeCesarDTO decodificarCifraDeCesarPersistida(Long id) {
//        CifraDeCesar cifraDeCesar = cifraDeCesarRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado!"));
//        Optional<Usuario> usuario = usuarioRepository.findById(cifraDeCesar.getUsuarioId());
//        CifraDeCesarDTO cifraDeCesarDTO = decodificarCifraDeCesar(cifraDeCesar.getMensagem(), usuario.get().getSenhaCriptografada());
//        cifraDeCesarDTO.setId(cifraDeCesar.getId());
//        return cifraDeCesarDTO;
//    }

    public List<CifraDeCesarDTO> buscarTodas() {
        return CifraDeCesarMapper.toCifraDeCesarDTOList(cifraDeCesarRepository.findAll());
    }

//    public CifraDeCesarDTO buscarCifra(Long id) {
//        return decodificarCifraDeCesarPersistida(id);
//    }
}
