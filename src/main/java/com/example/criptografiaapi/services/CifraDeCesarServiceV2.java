package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.dtos.DecodificarCifraDeCesarDTO;
import com.example.criptografiaapi.mappers.CifraDeCesarMapper;
import com.example.criptografiaapi.models.CifraDeCesar;
import com.example.criptografiaapi.repositories.CifraDeCesarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class CifraDeCesarServiceV2 {

    @Autowired
    CifraDeCesarRepository cifraDeCesarRepository;

    public List<CifraDeCesarDTO> buscarTodas() {
        return CifraDeCesarMapper.toCifraDeCesarDTOList(cifraDeCesarRepository.findAll());
    }

    public CifraDeCesarDTO buscarCifra(Long id, int senha) {
        return decodificarCifraDeCesarPersistida(id, senha);
    }

    public CifraDeCesarDTO decodificarCifraDeCesarPersistida(Long id, int senha) {
        CifraDeCesar cifraDeCesar = cifraDeCesarRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado!"));
        DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO = new DecodificarCifraDeCesarDTO();
        decodificarCifraDeCesarDTO.setMensagem(cifraDeCesar.getMensagem());
        decodificarCifraDeCesarDTO.setDecricao(cifraDeCesar.getDescricao());
        decodificarCifraDeCesarDTO.setSenha(senha);
        CifraDeCesarDTO cifraDeCesarDTO = decodificarCifraDeCesar(decodificarCifraDeCesarDTO);
        cifraDeCesarDTO.setId(cifraDeCesar.getId());
        cifraDeCesarDTO.setDescricao(cifraDeCesar.getDescricao());
        return cifraDeCesarDTO;
    }

    public static String removeAcentos(String letra) {
        String normalizer = Normalizer.normalize(letra, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizer).replaceAll("");
    }

    public CifraDeCesarDTO criptografar(CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        if(codificarCifraDeCesarDTO.getSenha() < 0 || codificarCifraDeCesarDTO.getSenha() > 999999) {
            throw new RuntimeException("A senha deve conter 6 dígitos númericos");
        }
        List<String> rotorUm = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        List<String> rotorDois = Arrays.asList("h", "i", "w", "z", "o", "r", "x", "a", "b", "y", "v", "f", "t", "p", "e", "n", "u", "l", "c", "m", "q", "k", "s", "g", "j", "d");
        List<String> rotorTres = Arrays.asList("z", "y", "x", "w", "v", "u", "t", "s", "r", "q", "p", "o", "n", "m", "l", "k", "j", "i", "h", "g", "f", "e", "d", "c", "b", "a");
        List<List<String>> rotores = Arrays.asList(rotorUm, rotorDois, rotorTres);
        int senha = codificarCifraDeCesarDTO.getSenha();
        List<Integer> senhaList = new ArrayList<>();
        do {
            senhaList.add(0, senha % 10);
            senha /= 10;
        } while (senha > 0);
        int indiceSenha = 0;
        int indiceRotor = 0;
        StringBuilder mensagemCodificada = new StringBuilder();
        for (int i = 0; i < codificarCifraDeCesarDTO.getMensagem().length(); i++) {
            int indiceLetra = 0;
            String letraParaCifrar = removeAcentos(String.valueOf(codificarCifraDeCesarDTO.getMensagem().charAt(i)));
            for (int j = 0; j < rotorUm.size(); j++) {
                if (rotores.get(indiceRotor).get(j).equalsIgnoreCase(letraParaCifrar)) {
                    indiceLetra = j;
                    break;
                }
            }
            int indiceLetraCifrada = indiceLetra + senhaList.get(indiceSenha);

            while (indiceLetraCifrada > 25) {
                indiceLetraCifrada -= 26;
            }
            if (letraParaCifrar.equals(" ")) { mensagemCodificada.append(" ");
            } else {
                mensagemCodificada.append(rotores.get(indiceRotor).get(indiceLetraCifrada));
            }
            if (indiceSenha > 4) { indiceSenha = 0;
            } else {
                indiceSenha++;
            }
            if (indiceRotor > 1) { indiceRotor = 0;
            } else {
                indiceRotor++;
            }
        }
        CifraDeCesar cifraDeCesar = new CifraDeCesar();
        cifraDeCesar.setMensagem(mensagemCodificada.toString());
        cifraDeCesar.setSenha(codificarCifraDeCesarDTO.getSenha());
        cifraDeCesar.setDataDaCodificacao(LocalDateTime.now());
        cifraDeCesar.setDescricao(codificarCifraDeCesarDTO.getDescricao());
        cifraDeCesarRepository.save(cifraDeCesar);
        return CifraDeCesarMapper.toCifraDeCesarDTO(cifraDeCesar);
    }

    public CifraDeCesarDTO decodificarCifraDeCesar(DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO) {
        if(decodificarCifraDeCesarDTO.getSenha() < 0 || decodificarCifraDeCesarDTO.getSenha() > 999999) {
            throw new RuntimeException("A senha deve conter 6 dígitos númericos");
        }
        List<String> rotorUm = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        List<String> rotorDois = Arrays.asList("h", "i", "w", "z", "o", "r", "x", "a", "b", "y", "v", "f", "t", "p", "e", "n", "u", "l", "c", "m", "q", "k", "s", "g", "j", "d");
        List<String> rotorTres = Arrays.asList("z", "y", "x", "w", "v", "u", "t", "s", "r", "q", "p", "o", "n", "m", "l", "k", "j", "i", "h", "g", "f", "e", "d", "c", "b", "a");
        List<List<String>> rotores = Arrays.asList(rotorUm, rotorDois, rotorTres);
        int senha = decodificarCifraDeCesarDTO.getSenha();
        List<Integer> senhaList = new ArrayList<>();
        do {
            senhaList.add(0, senha % 10);
            senha /= 10;
        } while (senha > 0);
        int indiceSenha = 0;
        int indiceRotor = 0;
        StringBuilder mensagemCodificada = new StringBuilder();
        for (int i = 0; i < decodificarCifraDeCesarDTO.getMensagem().length(); i++) {
            int indiceLetra = 0;
            String letraParaCifrar = removeAcentos(String.valueOf(decodificarCifraDeCesarDTO.getMensagem().charAt(i)));
            for (int j = 0; j < rotorUm.size(); j++) {
                if (rotores.get(indiceRotor).get(j).equalsIgnoreCase(letraParaCifrar)) {
                    indiceLetra = j;
                    break;
                }
            }
            int indiceLetraCifrada = indiceLetra - senhaList.get(indiceSenha);

            while (indiceLetraCifrada < 0) {
                indiceLetraCifrada += 26;
            }
            if (letraParaCifrar.equals(" ")) { mensagemCodificada.append(" ");
            } else {
                mensagemCodificada.append(rotores.get(indiceRotor).get(indiceLetraCifrada));
            }
            if (indiceSenha > 4) { indiceSenha = 0;
            } else {
                indiceSenha++;
            }
            if (indiceRotor > 1) { indiceRotor = 0;
            } else {
                indiceRotor++;
            }
        }
        CifraDeCesar cifraDeCesar = new CifraDeCesar();
        cifraDeCesar.setMensagem(mensagemCodificada.toString());
        cifraDeCesar.setSenha(decodificarCifraDeCesarDTO.getSenha());
        cifraDeCesar.setDataDaCodificacao(LocalDateTime.now());
        return CifraDeCesarMapper.toCifraDeCesarDTO(cifraDeCesar);
    }
}
