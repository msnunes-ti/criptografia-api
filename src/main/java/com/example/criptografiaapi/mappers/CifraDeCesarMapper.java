package com.example.criptografiaapi.mappers;

import com.example.criptografiaapi.dtos.CifraDeCesarDTO;
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
            cifraDeCesarDTO.setSenha(c.getSenha());
            cifraDeCesarDTOList.add(cifraDeCesarDTO);
        }
        return cifraDeCesarDTOList;
    }


}
