package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.dtos.DecodificarCifraDeCesarDTO;
import com.example.criptografiaapi.mappers.CifraDeCesarMapper;
import com.example.criptografiaapi.models.CifraDeCesar;
import com.example.criptografiaapi.models.Usuario;
import com.example.criptografiaapi.repositories.CifraDeCesarRepository;
import com.example.criptografiaapi.repositories.UsuarioRepository;
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
    @Autowired
    UsuarioRepository usuarioRepository;

    private CifraDeCesar buscarCifraPeloId(Long id) {
        return cifraDeCesarRepository.findById(id).orElseThrow(() -> new RuntimeException("Cifra não encontrada com este ID"));
    }

    private Usuario buscarUsuarioPeloId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public List<CifraDeCesarDTO> buscarTodasAsCifrasAindaCodificadas() {
        return CifraDeCesarMapper.toCifraDeCesarDTOList(cifraDeCesarRepository.findAll());
    }

    public List<CifraDeCesarDTO> buscarTodasAsCifrasDoUsuarioJaDecodificadas(Long id) {
        List<CifraDeCesar> cifraDeCesarList = cifraDeCesarRepository.findAllByUsuarioId(id);
        List<CifraDeCesarDTO> cifraDeCesarDTOList = new ArrayList<>();
        for(CifraDeCesar c: cifraDeCesarList) {
            CifraDeCesarDTO cifraDeCesarDTO = decodificarCifraPersistida(CifraDeCesarMapper.toDecodificarCifraDeCesarDTO(c));
            cifraDeCesarDTOList.add(cifraDeCesarDTO);
        }
        return cifraDeCesarDTOList;
    }

    public CifraDeCesarDTO buscarDecodificada(Long id) {
        CifraDeCesar cifraDeCesar = buscarCifraPeloId(id);
        return decodificarCifraPersistida(CifraDeCesarMapper.toDecodificarCifraDeCesarDTO(cifraDeCesar));
    }

    public static String removerAcentos(String letra) {
        String normalizer = Normalizer.normalize(letra, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizer).replaceAll("");
    }

    public CifraDeCesarDTO criptografarDepoisPersistir(CodificarCifraDeCesarDTO codificarCifraDeCesarDTO) {
        Usuario usuario = buscarUsuarioPeloId(codificarCifraDeCesarDTO.getUsuarioId());
        CifraDeCesar cifraDeCesar = new CifraDeCesar();
        cifraDeCesar.setMensagem(criptografarMensagem(codificarCifraDeCesarDTO.getMensagem(), usuario.getSenhaCriptografada()));
        cifraDeCesar.setUsuarioId(usuario.getId());
        cifraDeCesar.setDataDaCodificacao(LocalDateTime.now());
        cifraDeCesar.setDescricao(codificarCifraDeCesarDTO.getDescricao());
        cifraDeCesarRepository.save(cifraDeCesar);
        return CifraDeCesarMapper.toCifraDeCesarDTO(cifraDeCesar);
    }

    public String criptografarMensagem(String mensagem, Integer senhaCriptografada) {

        if(senhaCriptografada < 0 || senhaCriptografada > 999999) {
            throw new RuntimeException("A senha deve conter 6 dígitos númericos");
        }
        List<String> rotorUm = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        List<String> rotorDois = Arrays.asList("h", "i", "w", "z", "o", "r", "x", "a", "b", "y", "v", "f", "t", "p", "e", "n", "u", "l", "c", "m", "q", "k", "s", "g", "j", "d");
        List<String> rotorTres = Arrays.asList("z", "y", "x", "w", "v", "u", "t", "s", "r", "q", "p", "o", "n", "m", "l", "k", "j", "i", "h", "g", "f", "e", "d", "c", "b", "a");
        List<List<String>> rotores = Arrays.asList(rotorUm, rotorDois, rotorTres);
        List<Integer> senhaList = new ArrayList<>();
        do {
            senhaList.add(0, senhaCriptografada % 10);
            senhaCriptografada /= 10;
        } while (senhaCriptografada > 0);
        int indiceSenha = 0;
        int indiceRotor = 0;
        StringBuilder mensagemCodificada = new StringBuilder();
        for (int i = 0; i < mensagem.length(); i++) {
            int indiceLetra = 0;
            String letraParaCifrar = removerAcentos(String.valueOf(mensagem.charAt(i)));
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
        return mensagemCodificada.toString();
    }

    public CifraDeCesarDTO decodificarCifraPersistida(DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO) {
        Usuario usuario = buscarUsuarioPeloId(decodificarCifraDeCesarDTO.getUsuarioId());
        CifraDeCesarDTO cifraDeCesarDTO = new CifraDeCesarDTO();
        cifraDeCesarDTO.setId(usuario.getId());
        cifraDeCesarDTO.setMensagem(decodificarMensagem(decodificarCifraDeCesarDTO.getMensagem(), usuario.getSenhaCriptografada()));
        cifraDeCesarDTO.setDescricao(decodificarCifraDeCesarDTO.getDecricao());
        cifraDeCesarDTO.setDataDaCodificacao(decodificarCifraDeCesarDTO.getDataDaCodificacao());
        return cifraDeCesarDTO;
    }

    public String decodificarMensagem(String mensagem, Integer senhaCriptografada) {
        if(senhaCriptografada < 0 || senhaCriptografada > 999999) {
            throw new RuntimeException("A senha deve conter 6 dígitos númericos");
        }
        List<String> rotorUm = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        List<String> rotorDois = Arrays.asList("h", "i", "w", "z", "o", "r", "x", "a", "b", "y", "v", "f", "t", "p", "e", "n", "u", "l", "c", "m", "q", "k", "s", "g", "j", "d");
        List<String> rotorTres = Arrays.asList("z", "y", "x", "w", "v", "u", "t", "s", "r", "q", "p", "o", "n", "m", "l", "k", "j", "i", "h", "g", "f", "e", "d", "c", "b", "a");
        List<List<String>> rotores = Arrays.asList(rotorUm, rotorDois, rotorTres);
        List<Integer> senhaList = new ArrayList<>();
        do {
            senhaList.add(0, senhaCriptografada % 10);
            senhaCriptografada /= 10;
        } while (senhaCriptografada > 0);
        int indiceSenha = 0;
        int indiceRotor = 0;
        StringBuilder mensagemDecodificada = new StringBuilder();
        for (int i = 0; i < mensagem.length(); i++) {
            int indiceLetra = 0;
            String letraParaCifrar = removerAcentos(String.valueOf(mensagem.charAt(i)));
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
            if (letraParaCifrar.equals(" ")) { mensagemDecodificada.append(" ");
            } else {
                mensagemDecodificada.append(rotores.get(indiceRotor).get(indiceLetraCifrada));
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
        return mensagemDecodificada.toString();
    }

    public void deletarCifraDeCesar(Long id) {
        CifraDeCesar cifraDeCesar = buscarCifraPeloId(id);
        cifraDeCesarRepository.delete(cifraDeCesar);
    }
}
