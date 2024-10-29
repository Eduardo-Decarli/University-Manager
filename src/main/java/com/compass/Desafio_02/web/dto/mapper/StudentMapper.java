package com.compass.Desafio_02.web.dto.mapper;

import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.web.dto.StudentCreateDto;
import com.compass.Desafio_02.web.dto.StudentResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {

    public static Student toUsuario(StudentCreateDto createDto){
        return new ModelMapper().map(createDto, Student.class);
    }

    public static StudentResponseDto toDto(Student student){
        String role = student.getRole().name().substring("ROLE_".length());
        PropertyMap<Student, StudentResponseDto> props = new PropertyMap<Student, StudentResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(Role.valueOf(role));
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(student, StudentResponseDto.class);
    }

    public static List<StudentResponseDto> toListDto(List<Student> students){
        return students.stream().map(student -> toDto(student)).collect(Collectors.toList());
    }
}
