package com.compass.Desafio_02.web.dto;

import com.compass.Desafio_02.entities.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class DisciplineCreateDto {

    @NotBlank(message = "The name can't be empty")
    private String name;

    @Size(min = 5, max = 500, message = "The description must be between 5 and 500 characters")
    private String description;

    @NotNull(message = "Full Professor ID is required")
    private String mainTeacherEmail;

    @NotNull(message = "Substitute Teacher ID is required")
    private String subsTeacherEmail;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @Size(min = 1, message = "At least one student ID is required")
    private List<Student> students;
}