package com.compass.Desafio_02.web.dto;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.entities.Discipline;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CourseCreateDto {

    @NotBlank(message = "The name can't be empty")
    private String name;

    @NotBlank(message = "The description can't be empty")
    private String description;

    @NotNull(message = "Coordinator is required")
    private Long coordinatorId;

}
