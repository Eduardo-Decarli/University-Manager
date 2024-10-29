package com.compass.Desafio_02.repositories;

import com.compass.Desafio_02.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
