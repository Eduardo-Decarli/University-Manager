package com.compass.Desafio_02.web.dto.mapper;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Discipline;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.entities.Teacher;
import com.compass.Desafio_02.web.dto.DisciplineCreateDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DisciplineMapper {

    public static Discipline toDiscipline(DisciplineCreateDto createDto) {
        Discipline discipline = new ModelMapper().map(createDto, Discipline.class);
        return discipline;
    }

    public static DisciplineResponseDto toDto(Discipline discipline) {
        return new ModelMapper().map(discipline, DisciplineResponseDto.class);
    }

    public static List<DisciplineResponseDto> toListDto(List<Discipline> disciplines) {
        return disciplines.stream()
                .map(DisciplineMapper::toDto)
                .collect(Collectors.toList());
    }
}
