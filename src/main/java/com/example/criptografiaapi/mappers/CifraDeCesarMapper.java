package com.example.criptografiaapi.mappers;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
import com.example.criptografiaapi.dtos.CodificarCifraDeCesarDTO;
import com.example.criptografiaapi.dtos.DecodificarCifraDeCesarDTO;
import com.example.criptografiaapi.models.CifraDeCesarModel;

import java.util.ArrayList;
import java.util.List;

public class CifraDeCesarMapper {

    public static List<CifraDeCesarDTO> toCifraDeCesarDTOList(List<CifraDeCesarModel> cifraDeCesarModelList) {
        List<CifraDeCesarDTO> cifraDeCesarDTOList = new ArrayList<>();
        for (CifraDeCesarModel c : cifraDeCesarModelList) {
            CifraDeCesarDTO cifraDeCesarDTO = new CifraDeCesarDTO();
            cifraDeCesarDTO.setId(c.getId());
            cifraDeCesarDTO.setMensagem(c.getMensagem());
            cifraDeCesarDTO.setDescricao(c.getDescricao());
            cifraDeCesarDTO.setDataDaCodificacao(c.getDataDaCodificacao());
            cifraDeCesarDTOList.add(cifraDeCesarDTO);
        }
        return cifraDeCesarDTOList;
    }

    public static CifraDeCesarDTO toCifraDeCesarDTO(CifraDeCesarModel cifraDeCesarModel) {
        CifraDeCesarDTO cifraDeCesarDTO = new CifraDeCesarDTO();
        cifraDeCesarDTO.setId(cifraDeCesarModel.getId());
        cifraDeCesarDTO.setMensagem(cifraDeCesarModel.getMensagem());
        cifraDeCesarDTO.setDescricao(cifraDeCesarModel.getDescricao());
        cifraDeCesarDTO.setDataDaCodificacao(cifraDeCesarModel.getDataDaCodificacao());
        return cifraDeCesarDTO;
    }

    public static CifraDeCesarModel toCifraDeCesar(CifraDeCesarDTO cifraDeCesarDTO) {
        CifraDeCesarModel cifraDeCesarModel = new CifraDeCesarModel();
        cifraDeCesarModel.setId(cifraDeCesarDTO.getId());
        cifraDeCesarModel.setMensagem(cifraDeCesarDTO.getMensagem());
        cifraDeCesarModel.setDescricao(cifraDeCesarDTO.getDescricao());
        cifraDeCesarModel.setDataDaCodificacao(cifraDeCesarDTO.getDataDaCodificacao());
        return cifraDeCesarModel;
    }

    public static DecodificarCifraDeCesarDTO toDecodificarCifraDeCesarDTO(CifraDeCesarModel cifraDeCesarModel) {
        DecodificarCifraDeCesarDTO decodificarCifraDeCesarDTO = new DecodificarCifraDeCesarDTO();
        decodificarCifraDeCesarDTO.setMensagem(cifraDeCesarModel.getMensagem());
        decodificarCifraDeCesarDTO.setDecricao(cifraDeCesarModel.getDescricao());
        decodificarCifraDeCesarDTO.setUsuarioId(cifraDeCesarModel.getUsuarioId());
        decodificarCifraDeCesarDTO.setDataDaCodificacao(cifraDeCesarModel.getDataDaCodificacao());
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
