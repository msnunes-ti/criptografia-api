package com.example.criptografiaapi.repositories;

import com.example.criptografiaapi.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleModelRepository extends JpaRepository<RoleModel, Long> {
}
