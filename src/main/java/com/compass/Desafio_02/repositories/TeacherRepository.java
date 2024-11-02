package com.compass.Desafio_02.repositories;

import com.compass.Desafio_02.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByEmail(String email);
}
