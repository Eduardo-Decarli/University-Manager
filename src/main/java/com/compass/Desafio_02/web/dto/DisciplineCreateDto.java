package com.compass.Desafio_02.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class DisciplineCreateDto {

    @NotBlank(message = "The name can't be empty")
    private String name;

    private String description;

    @NotNull(message = "Full Professor ID is required")
    private Long fullProfessorId;

    private Long substituteTeacherId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    private List<Long> studentIds;
}