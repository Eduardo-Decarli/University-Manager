package com.compass.Desafio_02.web.dto;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.entities.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DisciplineResponseDto {

    private Long id;
    private String name;
    private String description;
    private String mainTeacherEmail;
    private String subsTeacher;
    private List<Student> students;
}
