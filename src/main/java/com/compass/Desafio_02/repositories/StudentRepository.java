package com.compass.Desafio_02.repositories;

import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);
}
