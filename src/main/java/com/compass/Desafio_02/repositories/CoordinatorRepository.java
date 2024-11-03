package com.compass.Desafio_02.repositories;

import com.compass.Desafio_02.entities.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {
    Optional<Coordinator> findByEmail(String email);
}
