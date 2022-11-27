package com.example.criptografiaapi.mappers;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
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
//            cifraDeCesarDTO.setSenha(c.getSenha());
            cifraDeCesarDTOList.add(cifraDeCesarDTO);
        }
        return cifraDeCesarDTOList;
    }

    public static CifraDeCesarDTO toCifraDeCesarDTO(CifraDeCesar cifraDeCesar) {
        CifraDeCesarDTO cifraDeCesarDTO = new CifraDeCesarDTO();
        cifraDeCesarDTO.setId(cifraDeCesar.getId());
        cifraDeCesarDTO.setMensagem(cifraDeCesar.getMensagem());
        cifraDeCesarDTO.setDescricao(cifraDeCesar.getDescricao());
//        cifraDeCesarDTO.setSenha(cifraDeCesar.getSenha());
        cifraDeCesarDTO.setDataDaCodificacao(cifraDeCesar.getDataDaCodificacao());
        return cifraDeCesarDTO;
    }

    public static  CifraDeCesarDTO toCifraDeCesarDTO2(DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO) {
        CifraDeCesarDTO cifraDeCesarDTO = new CifraDeCesarDTO();
        cifraDeCesarDTO.setMensagem(decodificarCifraDeCesarDTO.getMensagem());
        cifraDeCesarDTO.setDescricao(decodificarCifraDeCesarDTO.getDecricao());
//        cifraDeCesarDTO.setSenha(decodificarCifraDeCesarDTO.getSenha());
        return cifraDeCesarDTO;
    }
}
