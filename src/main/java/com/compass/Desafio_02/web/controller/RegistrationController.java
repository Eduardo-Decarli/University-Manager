package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Registration;
import com.compass.Desafio_02.services.RegistrationServices;
import com.compass.Desafio_02.web.dto.RegistrationCreateDto;
import com.compass.Desafio_02.web.dto.RegistrationResponseDto;
import com.compass.Desafio_02.web.exception.handler.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registration")
@Validated
public class RegistrationController {

    private final RegistrationServices services;

    public RegistrationController(RegistrationServices services){
        this.services = services;
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<RegistrationResponseDto> create(@Valid @RequestBody RegistrationCreateDto registration) {
        RegistrationResponseDto response = services.createRegistration(registration);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<List<RegistrationResponseDto>> getAllRegistrations(){
        List<RegistrationResponseDto> registrations = services.getAllRegistrations();
        return ResponseEntity.ok().body(registrations);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<RegistrationResponseDto> getRegistrationById(@PathVariable long id){
        RegistrationResponseDto registration = services.getRegistrationById(id);
        return ResponseEntity.ok().body(registration);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<RegistrationResponseDto> updateRegistration(@Valid @PathVariable Long id, @RequestBody RegistrationCreateDto registration) {
        RegistrationResponseDto update = services.updateRegistration(id, registration);
        return ResponseEntity.ok().body(update);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> deleteRegistrationById(@PathVariable long id) {
        services.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
