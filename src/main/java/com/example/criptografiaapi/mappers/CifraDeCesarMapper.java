package com.example.criptografiaapi.mappers;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.dtos.DecodificarCifraDeCesarDTO;
import com.example.criptografiaapi.models.CifraDeCesar;

import java.util.ArrayList;
import java.util.List;

public class CifraDeCesarMapper {

    public static List<CifraDeCesarDTO> toCifraDeCesarDTOList(List<CifraDeCesar> cifraDeCesarList) {
        List<CifraDeCesarDTO> cifraDeCesarDTOList = new ArrayList<>();
        for (CifraDeCesar c : cifraDeCesarList) {
            CifraDeCesarDTO cifraDeCesarDTO = new CifraDeCesarDTO();
            cifraDeCesarDTO.setId(c.getId());
            cifraDeCesarDTO.setMensagem(c.getMensagem());
            cifraDeCesarDTO.setDescricao(c.getDescricao());
            cifraDeCesarDTO.setDataDaCodificacao(c.getDataDaCodificacao());
            cifraDeCesarDTOList.add(cifraDeCesarDTO);
        }
        return cifraDeCesarDTOList;
    }

    public static CifraDeCesarDTO toCifraDeCesarDTO(CifraDeCesar cifraDeCesar) {
        CifraDeCesarDTO cifraDeCesarDTO = new CifraDeCesarDTO();
        cifraDeCesarDTO.setId(cifraDeCesar.getId());
        cifraDeCesarDTO.setMensagem(cifraDeCesar.getMensagem());
        cifraDeCesarDTO.setDescricao(cifraDeCesar.getDescricao());
        cifraDeCesarDTO.setDataDaCodificacao(cifraDeCesar.getDataDaCodificacao());
        return cifraDeCesarDTO;
    }

    public static CifraDeCesarDTO toCifraDeCesarDTO2(DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO) {
        CifraDeCesarDTO cifraDeCesarDTO = new CifraDeCesarDTO();
        cifraDeCesarDTO.setId(cifraDeCesarDTO.getId());
        cifraDeCesarDTO.setMensagem(decodificarCifraDeCesarDTO.getMensagem());
        cifraDeCesarDTO.setDescricao(decodificarCifraDeCesarDTO.getDecricao());
        cifraDeCesarDTO.setDataDaCodificacao(decodificarCifraDeCesarDTO.getDataDaCodificacao());
        return cifraDeCesarDTO;
    }

    public static DecodificarCifraDeCesarDTO toDecodificarCifraDeCesarDTO(CifraDeCesar cifraDeCesar) {
        DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO = new DecodificarCifraDeCesarDTO();
        decodificarCifraDeCesarDTO.setMensagem(cifraDeCesar.getMensagem());
        decodificarCifraDeCesarDTO.setDecricao(cifraDeCesar.getDescricao());
        decodificarCifraDeCesarDTO.setUsuarioId(cifraDeCesar.getUsuarioId());
        decodificarCifraDeCesarDTO.setDataDaCodificacao(cifraDeCesar.getDataDaCodificacao());
        return decodificarCifraDeCesarDTO;
    }

    public static CodificarCifraDeCesarDTO toCodificarCifraDeCesarDTO(CifraDeCesarDTO cifraDeCesarDTO) {
        CodificarCifraDeCesarDTO codificarCifraDeCesarDTO = new CodificarCifraDeCesarDTO();
        codificarCifraDeCesarDTO.setMensagem(cifraDeCesarDTO.getMensagem());
        codificarCifraDeCesarDTO.setDescricao(cifraDeCesarDTO.getDescricao());
        codificarCifraDeCesarDTO.setUsuarioId(cifraDeCesarDTO.getId());
        return codificarCifraDeCesarDTO;
    }
}
