package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.services.CoordinatorServices;
import com.compass.Desafio_02.web.dto.CoordinatorCreateDto;
import com.compass.Desafio_02.web.dto.CoordinatorResponseDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
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

    @PostMapping
    public ResponseEntity<CoordinatorResponseDto> create(@Valid @RequestBody CoordinatorCreateDto coordinator) {
        CoordinatorResponseDto response = services.createCoordinator(coordinator);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CoordinatorResponseDto>> getAllCoordinator() {
        List<CoordinatorResponseDto> Coordinators = services.getAllCoordinators();
        return ResponseEntity.ok().body(Coordinators);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coordinator> getCoordinatorById(@PathVariable long id){
        Coordinator coordinator = services.getCoordinatorById(id);
        return ResponseEntity.ok().body(coordinator);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoordinatorResponseDto> updateCoordinator(@Valid @PathVariable Long id, @RequestBody CoordinatorCreateDto coordinator) {
        CoordinatorResponseDto updatedCoordinator = services.updateCoordinator(id, coordinator);
        return ResponseEntity.ok().body(updatedCoordinator);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoordinatorById(@PathVariable long id) {
        services.deleteCoordinator(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<CoordinatorResponseDto> myData(@PathVariable Long id) {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        CoordinatorResponseDto response = services.getMyData(id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/me/{id}")
    public ResponseEntity<CoordinatorResponseDto> updateMyData(@Valid @PathVariable Long id, @RequestBody CoordinatorCreateDto coordinator) {
        CoordinatorResponseDto updatedCoordinator = services.updateMyData(id, coordinator);
        return ResponseEntity.ok().body(updatedCoordinator);
    }

    @GetMapping("/me/{id}/course")
    public ResponseEntity<CourseResponseDto> myCourse(@PathVariable Long id) {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        CourseResponseDto course = services.getMyCourse(id);
        return ResponseEntity.ok().body(course);
    }

    @GetMapping("/me/{id}/course/disciplines")
    public ResponseEntity<List<DisciplineResponseDto>> myCourseDisciplines(@PathVariable Long id) {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        List<DisciplineResponseDto> response = services.getDisciplinesInCourse(id);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/me/{id}/disciplines")
    public ResponseEntity<List<DisciplineResponseDto>> myDisciplines(@PathVariable Long id) {

        // TO-DO -> Acessar as proprias disciplinas
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        List<DisciplineResponseDto> response = services.getMyDisciplines(id);

        return ResponseEntity.ok().body(response);
    }

}
