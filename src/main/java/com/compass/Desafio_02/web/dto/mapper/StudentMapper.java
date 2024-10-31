package com.compass.Desafio_02.web.dto.mapper;

import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.web.dto.StudentCreateDto;
import com.compass.Desafio_02.web.dto.StudentResponseDto;
import com.compass.Desafio_02.web.dto.TeacherResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {

    public static Student toStudent(StudentCreateDto createDto){
        return new ModelMapper().map(createDto, Student.class);
    }

    public static StudentResponseDto toDto(Student student){
        return new ModelMapper().map(student, StudentResponseDto.class);

    }

    public static List<StudentResponseDto> toListDto(List<Student> students){
        return students.stream().map(student -> toDto(student)).collect(Collectors.toList());
    }
}
