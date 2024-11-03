package com.compass.Desafio_02.web.dto.mapper;


import com.compass.Desafio_02.entities.Teacher;
import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.web.dto.TeacherCreateDto;
import com.compass.Desafio_02.web.dto.TeacherResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherMapper {

    public static Teacher toTeacher(TeacherCreateDto createDto){
        return new ModelMapper().map(createDto, Teacher.class);
    }

    public static Teacher toTeacher(TeacherResponseDto responseDto){
        return new ModelMapper().map(responseDto, Teacher.class);
    }

    public static TeacherResponseDto toDto(Teacher teacher) {
        return new ModelMapper().map(teacher, TeacherResponseDto.class);
    }

    public static List<TeacherResponseDto> toListDto(List<Teacher> teachers){
        return teachers.stream().map(teacher -> toDto(teacher)).collect(Collectors.toList());
    }
}
