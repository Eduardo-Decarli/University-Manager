package com.compass.Desafio_02.web.dto;

import com.compass.Desafio_02.entities.Course;
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

    private String mainTeacherEmail;

    private String subsTeacherEmail;

}