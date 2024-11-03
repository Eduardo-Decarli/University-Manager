package com.compass.Desafio_02.web.dto.mapper;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Registration;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.web.dto.RegistrationCreateDto;
import com.compass.Desafio_02.web.dto.RegistrationResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class RegistrationMapper {

    public static Registration toRegistration(RegistrationCreateDto createDto) {
        Registration registration = new ModelMapper().map(createDto, Registration.class);
        return registration;
    }

    public static RegistrationResponseDto toDto(Registration registration) {
        return new ModelMapper().map(registration, RegistrationResponseDto.class);
    }

    public static List<RegistrationResponseDto> toListDto(List<Registration> registrations) {
        return registrations.stream()
                .map(RegistrationMapper::toDto)
                .collect(Collectors.toList());
    }
}
