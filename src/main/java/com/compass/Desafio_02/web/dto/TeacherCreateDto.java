package com.compass.Desafio_02.web.dto;

import com.compass.Desafio_02.entities.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TeacherCreateDto {

    @NotBlank(message = "The first name can't be empty")
    @Pattern(
            regexp = "^[A-Z][a-zà-ÿ]+$",
            message = "The name must start with a capital letter."
    )
    private String firstName;

    @NotBlank(message = "The last name can't be empty")
    @Pattern(
            regexp = "^[A-Z][a-zà-ÿ]+$",
            message = "The surname must begin with a capital letter."
    )
    private String lastName;

    @NotBlank(message = "The email can't be empty")
    @Email(message = "The email must have a valid format")
    private String email;

    @NotNull(message = "The date of birth cannot be empty")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @NotBlank(message = "Password cannot be empty")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "A password must be at least 8 characters long, with at least one saved letter, one lowercase letter, one number, and one special character."
    )
    private String password;

    @NotNull(message = "Role is required")
    private Role role = Role.ROLE_TEACHER;
}