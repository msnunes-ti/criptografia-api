package com.example.criptografiaapi.repositories;

import com.example.criptografiaapi.models.CifraDeCesarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CifraDeCesarRepository extends JpaRepository<CifraDeCesarModel, Long>{

    List<CifraDeCesarModel> findAllByUsuarioId(Long id);
}
