package com.compass.Desafio_02.web.dto;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Discipline;
import com.compass.Desafio_02.entities.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TeacherResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private List<Discipline> disciplines;
    private List<Course> courses;
}
