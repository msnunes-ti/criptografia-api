package com.example.criptografiaapi.services;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CriarCifraDeCesarDTO;
import com.example.criptografiaapi.dtos.DecodificarCifraDeCesarDTO;
import com.example.criptografiaapi.dtos.UsuarioLogadoDTO;
import com.example.criptografiaapi.mappers.CifraDeCesarMapper;
import com.example.criptografiaapi.models.CifraDeCesarModel;
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
    UsuarioLogadoDTO usuarioLogadoDTO;
    @Autowired
    UsuarioRepository usuarioRepository;

    private CifraDeCesarModel buscarCifraPeloId(Long id) {
        return cifraDeCesarRepository.findById(id).orElseThrow(() -> new RuntimeException("Cifra não encontrada com este ID"));
    }

    private Usuario buscarUsuarioPeloId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public List<CifraDeCesarDTO> buscarTodasAsCifrasAindaCodificadas() {
        return CifraDeCesarMapper.toCifraDeCesarDTOList(cifraDeCesarRepository.findAllByUsuarioId(usuarioLogadoDTO.getId()));
    }

    public List<CifraDeCesarDTO> buscarTodasAsCifrasDoUsuarioJaDecodificadas() {
        List<CifraDeCesarModel> cifraDeCesarModelList = cifraDeCesarRepository.findAllByUsuarioId(usuarioLogadoDTO.getId());
        List<CifraDeCesarDTO> cifraDeCesarDTOList = new ArrayList<>();
        for(CifraDeCesarModel c: cifraDeCesarModelList) {
            CifraDeCesarDTO cifraDeCesarDTO = decodificarCifraPersistida(CifraDeCesarMapper.toDecodificarCifraDeCesarDTO(c));
            cifraDeCesarDTOList.add(cifraDeCesarDTO);
        }
        return cifraDeCesarDTOList;
    }

    public CifraDeCesarDTO buscarDecodificada(Long id) {
        List<CifraDeCesarModel> cifraDeCesarModelList = cifraDeCesarRepository.findAllByUsuarioId(usuarioLogadoDTO.getId());
        if(cifraDeCesarModelList.size() == 0) {
            throw new RuntimeException("Cifra não encontrada para esse usuário");
        }
        CifraDeCesarModel cifraDeCesarModel = null;
        for (CifraDeCesarModel c: cifraDeCesarModelList) {
            if(c.getId().equals(id)) {
                cifraDeCesarModel = c;
                break;
            }
        }
        if(cifraDeCesarModel == null) {
            throw new RuntimeException("Cifra não encontrada para esse usuário");
        }
        return decodificarCifraPersistida(CifraDeCesarMapper.toDecodificarCifraDeCesarDTO(cifraDeCesarModel));
    }

    private static String removerAcentos(String letra) {
        String normalizer = Normalizer.normalize(letra, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizer).replaceAll("");
    }

    public CifraDeCesarDTO criptografarDepoisPersistir(CriarCifraDeCesarDTO criarCifraDeCesarDTO) {
        CifraDeCesarModel cifraDeCesarModel = new CifraDeCesarModel();
        cifraDeCesarModel.setUsuarioId(usuarioLogadoDTO.getId());
        cifraDeCesarModel.setMensagem(criptografarMensagem(criarCifraDeCesarDTO.getMensagem(), usuarioLogadoDTO.getSenhaCriptografada()));
        cifraDeCesarModel.setDescricao(criarCifraDeCesarDTO.getDescricao());
        cifraDeCesarModel.setDataDaCodificacao(LocalDateTime.now());
        cifraDeCesarRepository.save(cifraDeCesarModel);
        return CifraDeCesarMapper.toCifraDeCesarDTO(cifraDeCesarModel);
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
        CifraDeCesarDTO cifraDeCesarDTO = new CifraDeCesarDTO();
        cifraDeCesarDTO.setId(decodificarCifraDeCesarDTO.getId());
        cifraDeCesarDTO.setMensagem(decodificarMensagem(decodificarCifraDeCesarDTO.getMensagem(), usuarioLogadoDTO.getSenhaCriptografada()));
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
        CifraDeCesarModel cifraDeCesarModel = buscarCifraPeloId(id);
        if(!cifraDeCesarModel.getUsuarioId().equals(usuarioLogadoDTO.getId())) {
            throw new RuntimeException("Cifra não encontrada ou Não pertence a este usuário");
        }
        cifraDeCesarRepository.delete(cifraDeCesarModel);
    }

}
