package com.example.criptografiaapi.repositories;

import com.example.criptografiaapi.models.CifraDeCesar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CifraDeCesarRepository extends JpaRepository<CifraDeCesar, Long> {


}
