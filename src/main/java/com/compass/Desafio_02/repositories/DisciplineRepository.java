package com.compass.Desafio_02.repositories;

import com.compass.Desafio_02.entities.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    Discipline findDisciplineByName(String name);
}
