package com.compass.Desafio_02.web.dto.mapper;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.web.dto.CourseCreateDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CourseMapper {

    public static Course toCourse(CourseCreateDto createDto) {
        return new ModelMapper().map(createDto, Course.class);
    }

    public static CourseResponseDto toDto(Course course) {
        return new ModelMapper().map(course, CourseResponseDto.class);
    }

    public static List<CourseResponseDto> toListDto(List<Course> courses) {
        return courses.stream()
                .map(CourseMapper::toDto)
                .collect(Collectors.toList());
    }
}
