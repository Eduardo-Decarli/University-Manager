package com.compass.Desafio_02.web.dto;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RegistrationCreateDto {

    @NotNull(message = "Student is required")
    private Long studentId;

    @NotNull(message = "Course is required")
    private Long courseId;
}
