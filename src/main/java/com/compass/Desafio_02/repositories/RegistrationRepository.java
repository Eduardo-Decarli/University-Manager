package com.compass.Desafio_02.repositories;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByCourse(Course course);
}
