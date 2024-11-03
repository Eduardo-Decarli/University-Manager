package com.compass.Desafio_02.repositories;

import com.compass.Desafio_02.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
