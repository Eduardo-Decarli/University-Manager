package com.compass.Desafio_02.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class CourseCreateDto {

    @NotBlank(message = "The name can't be empty")
    private String name;

    @NotBlank(message = "The description can't be empty")
    private String description;

    @NotNull(message = "Coordinator ID is required")
    private Long coordinatorId;

    @NotNull(message = "Discipline IDs are required")
    @Size(min = 1, message = "At least one discipline ID is required")
    private List<Long> disciplineIds;
}
