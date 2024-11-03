package com.compass.Desafio_02.web.dto;

import com.compass.Desafio_02.entities.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class TeacherCreateDto {

    @NotBlank(message = "The first name can't be empty")
    private String firstName;

    @NotBlank(message = "The last name can't be empty")
    private String lastName;

    @NotBlank(message = "The email can't be empty")
    @Email(message = "The email must have a valid format")
    private String email;

    @NotNull(message = "The date of birth cannot be empty")
    private LocalDate birthDate;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "The password must be at least 8 characters long")
    private String password;

    @NotNull(message = "Role is required")
    private Role role = Role.ROLE_TEACHER;
}