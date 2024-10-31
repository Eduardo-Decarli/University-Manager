package com.compass.Desafio_02.repositories;

import com.compass.Desafio_02.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
