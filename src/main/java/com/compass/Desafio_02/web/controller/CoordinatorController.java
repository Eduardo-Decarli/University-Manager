package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.services.CoordinatorServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coordinator")
public class CoordinatorController {

    private final CoordinatorServices services;

    public CoordinatorController(CoordinatorServices services) {
        this.services = services;
    }

    @GetMapping("/course")
    public ResponseEntity<List<Coordinator>> getAllCoordinator() {
        List<Coordinator> Coordinators = services.getAllCoordinators();
        return ResponseEntity.ok().body(Coordinators);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coordinator> getCoordinatorById(@PathVariable long id) throws Exception {
        Coordinator coordinator = services.getCoordinatorById(id);
        return ResponseEntity.ok().body(coordinator);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coordinator> updateCoordinator(@PathVariable long id, @RequestBody Coordinator coordinator) throws Exception {
        Coordinator updatedCoordinator = services.updateCoordinator(id, coordinator);
        return ResponseEntity.ok().body(updatedCoordinator);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCoordinatorById(@PathVariable long id) throws Exception {
        services.deleteCoordinator(id);
        return ResponseEntity.noContent().build();
    }
}
