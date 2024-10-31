package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Registration;
import com.compass.Desafio_02.services.RegistrationServices;
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

    // ROLE_COORDINATOR
    @PostMapping
    public ResponseEntity<Registration> create(@Valid @RequestBody Registration registration) {
        Registration response = services.createRegistration(registration);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/registrations")
    public ResponseEntity<List<Registration>> getAllRegistrations(){
        List<Registration> registrations = services.getAllRegistrations();
        return ResponseEntity.ok().body(registrations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable long id){
        Registration registration = services.getRegistrationById(id);
        return ResponseEntity.ok().body(registration);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Registration> updateRegistration(@Valid @RequestBody Registration registration) {
        Registration update = services.updateRegistration(registration);
        return ResponseEntity.ok().body(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistrationById(@PathVariable long id) {
        services.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
