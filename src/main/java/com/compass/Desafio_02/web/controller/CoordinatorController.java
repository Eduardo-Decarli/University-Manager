package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.jwt.JwtUserDetails;
import com.compass.Desafio_02.services.CoordinatorServices;
import com.compass.Desafio_02.web.dto.CoordinatorCreateDto;
import com.compass.Desafio_02.web.dto.CoordinatorResponseDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    //@PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CoordinatorResponseDto> create(@Valid @RequestBody CoordinatorCreateDto coordinator) {
        CoordinatorResponseDto response = services.createCoordinator(coordinator);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<List<CoordinatorResponseDto>> getAllCoordinator() {
        List<CoordinatorResponseDto> Coordinators = services.getAllCoordinators();
        return ResponseEntity.ok().body(Coordinators);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CoordinatorResponseDto> getCoordinatorById(@PathVariable long id){
        CoordinatorResponseDto coordinator = services.getCoordinatorById(id);
        return ResponseEntity.ok().body(coordinator);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CoordinatorResponseDto> updateCoordinator(@PathVariable Long id, @Valid  @RequestBody CoordinatorCreateDto coordinator) {
        CoordinatorResponseDto updatedCoordinator = services.updateCoordinator(id, coordinator);
        return ResponseEntity.ok().body(updatedCoordinator);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> deleteCoordinatorById(@PathVariable long id) {
        services.deleteCoordinator(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CoordinatorResponseDto> myData(@AuthenticationPrincipal JwtUserDetails userDetails) {
        CoordinatorResponseDto response = services.getMyData(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CoordinatorResponseDto> updateMyData(@AuthenticationPrincipal JwtUserDetails userDetails, @RequestBody CoordinatorCreateDto coordinator) {
        CoordinatorResponseDto updatedCoordinator = services.updateMyData(userDetails.getId(), coordinator);
        return ResponseEntity.ok().body(updatedCoordinator);
    }

    @GetMapping("/me/course")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CourseResponseDto> myCourse(@AuthenticationPrincipal JwtUserDetails userDetails) {
        CourseResponseDto course = services.getMyCourse(userDetails.getId());
        return ResponseEntity.ok().body(course);
    }

    @GetMapping("/me/course/disciplines")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<List<DisciplineResponseDto>> myCourseDisciplines(@AuthenticationPrincipal JwtUserDetails userDetails) {
        List<DisciplineResponseDto> response = services.getDisciplinesInCourse(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/me/disciplines")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<List<DisciplineResponseDto>> myDisciplines(@AuthenticationPrincipal JwtUserDetails userDetails) {
        List<DisciplineResponseDto> response = services.getMyDisciplines(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

}
