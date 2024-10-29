package com.compass.Desafio_02.web.dto;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.enumeration.Role;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class StudentResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String address;
    private Role role;
    private Course course;

}
