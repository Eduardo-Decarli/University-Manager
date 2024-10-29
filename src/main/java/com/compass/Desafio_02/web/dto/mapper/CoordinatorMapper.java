package com.compass.Desafio_02.web.dto.mapper;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.web.dto.CoordinatorCreateDto;
import com.compass.Desafio_02.web.dto.CoordinatorResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class CoordinatorMapper {

    public static Coordinator toCoordinator(CoordinatorCreateDto createDto){
        return new ModelMapper().map(createDto, Coordinator.class);
    }

    public static CoordinatorResponseDto toDto(Coordinator coordinator){
        String role = coordinator.getRole().name().substring("ROLE_".length());
        PropertyMap<Coordinator, CoordinatorResponseDto> props = new PropertyMap<Coordinator, CoordinatorResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(Role.valueOf(role));
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(coordinator, CoordinatorResponseDto.class);
    }

    public static List<CoordinatorResponseDto> toListDto(List<Coordinator> coordinators){
        return coordinators.stream().map(coordinator -> toDto(coordinator)).collect(Collectors.toList());
    }
}
