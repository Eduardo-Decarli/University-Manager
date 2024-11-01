package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Registration;
import com.compass.Desafio_02.services.RegistrationServices;
import com.compass.Desafio_02.web.dto.RegistrationCreateDto;
import com.compass.Desafio_02.web.dto.RegistrationResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RegistrationResponseDto> create(@Valid @RequestBody RegistrationCreateDto registration) {
        RegistrationResponseDto response = services.createRegistration(registration);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/registrations")
    public ResponseEntity<List<RegistrationResponseDto>> getAllRegistrations(){
        List<RegistrationResponseDto> registrations = services.getAllRegistrations();
        return ResponseEntity.ok().body(registrations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationResponseDto> getRegistrationById(@PathVariable long id){
        RegistrationResponseDto registration = services.getRegistrationById(id);
        return ResponseEntity.ok().body(registration);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistrationResponseDto> updateRegistration(@Valid @PathVariable Long id, @RequestBody Registration registration) {
        RegistrationResponseDto update = services.updateRegistration(id, registration);
        return ResponseEntity.ok().body(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistrationById(@PathVariable long id) {
        services.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
