package com.compass.Desafio_02.web.dto.mapper;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.web.dto.CoordinatorCreateDto;
import com.compass.Desafio_02.web.dto.CoordinatorResponseDto;
import com.compass.Desafio_02.web.dto.TeacherResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class CoordinatorMapper {

    public static Coordinator toCoordinator(CoordinatorCreateDto createDto){
        return new ModelMapper().map(createDto, Coordinator.class);
    }

    public static CoordinatorResponseDto toDto(Coordinator coordinator){
        return new ModelMapper().map(coordinator, CoordinatorResponseDto.class);

    }

    public static List<CoordinatorResponseDto> toListDto(List<Coordinator> coordinators){
        return coordinators.stream().map(coordinator -> toDto(coordinator)).collect(Collectors.toList());
    }
}
