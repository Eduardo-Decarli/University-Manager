package com.compass.Desafio_02.web.dto;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.entities.Discipline;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CourseResponseDto {

    private Long id;
    private String name;
    private String description;
    private Coordinator coordinator;
    private List<Discipline> disciplines;
}
