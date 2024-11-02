package com.compass.Desafio_02.repositories;

import com.compass.Desafio_02.entities.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {
    Coordinator findByEmail(String email);
}
