package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.services.CoordinatorServices;
import com.compass.Desafio_02.web.dto.CoordinatorResponseDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import com.compass.Desafio_02.web.dto.StudentResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coordinator")
@Validated
public class CoordinatorController {

    private final CoordinatorServices services;

    public CoordinatorController(CoordinatorServices services) {
        this.services = services;
    }

    // ROLE_COORDINATOR
    @PostMapping
    public ResponseEntity<Coordinator> create(@Valid @RequestBody Coordinator coordinator) {
        Coordinator response = services.createCoordinator(coordinator);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Coordinator>> getAllCoordinator() {
        List<Coordinator> Coordinators = services.getAllCoordinators();
        return ResponseEntity.ok().body(Coordinators);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coordinator> getCoordinatorById(@PathVariable long id) throws Exception {
        Coordinator coordinator = services.getCoordinatorById(id);
        return ResponseEntity.ok().body(coordinator);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordinatorById(@PathVariable long id) throws Exception {
        services.deleteCoordinator(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CoordinatorResponseDto> myData() {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        return ResponseEntity.ok().build();
    }

    @PutMapping("/me")
    public ResponseEntity<Coordinator> updateCoordinator(@Valid @RequestBody Coordinator coordinator) throws Exception {
        Coordinator updatedCoordinator = services.updateCoordinator(coordinator);
        return ResponseEntity.ok().body(updatedCoordinator);
    }

    @GetMapping("/me/course")
    public ResponseEntity<CourseResponseDto> myCourse() {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/course/disciplines")
    public ResponseEntity<CourseResponseDto> myCourseDisciplines() {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/disciplines")
    public ResponseEntity<List<DisciplineResponseDto>> myDisciplines() {

        // TO-DO -> Acessar as proprias disciplinas
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        return ResponseEntity.ok().build();
    }

}
