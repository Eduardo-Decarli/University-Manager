package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.services.DisciplineServices;
import com.compass.Desafio_02.web.dto.DisciplineCreateDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discipline")
@Validated
public class DisciplineController {

    private final DisciplineServices services;

    public DisciplineController(DisciplineServices services){
        this.services = services;
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> create(@Valid @RequestBody DisciplineCreateDto discipline) {
        DisciplineResponseDto response = services.createDiscipline(discipline);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<List<DisciplineResponseDto>> getAllRDisciplines(){
        List<DisciplineResponseDto> disciplines = services.getAllDisciplines();
        return ResponseEntity.ok().body(disciplines);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> getDisciplineById(@PathVariable long id){
        DisciplineResponseDto discipline = services.getDisciplineById(id);
        return ResponseEntity.ok().body(discipline);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> updateDiscipline(@Valid @PathVariable Long id, @RequestBody DisciplineCreateDto discipline) {
        DisciplineResponseDto update = services.updateDiscipline(id, discipline);
        return ResponseEntity.ok().body(update);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> deleteDisciplineById(@PathVariable long id) {
        services.deleteDiscipline(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{disciplineName}/add/student/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> addStudentToDiscipline(@Valid @PathVariable String disciplineName, @PathVariable Long id) {
        DisciplineResponseDto discipline = services.addStudentInDisciplineByName(disciplineName, id);
        return ResponseEntity.ok().body(discipline);
    }

    @PatchMapping("/{disciplineName}/remove/student/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> removeStudentDiscipline(@Valid @PathVariable String disciplineName, @PathVariable Long id) {
        DisciplineResponseDto discipline = services.removeStudentInDisciplineByName(disciplineName, id);
        return ResponseEntity.ok().body(discipline);
    }

    @PatchMapping("/{disciplineName}/add/teacher/titular/{emailTeacher}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> addTitularTeacherDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.addTitularTeacherByDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @PatchMapping("/{disciplineName}/remove/teacher/titular/{emailTeacher}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> removeTitularTeacherDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.removeTitularTeacherDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @PatchMapping("/{disciplineName}/add/teacher/substitute/{emailTeacher}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> addSubstituteTeacherDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.addSubstituteTeacherDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @PatchMapping("/{disciplineName}/remove/teacher/substitute/{emailTeacher}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> removeSubstituteTeacherDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.removeSubstituteTeacherDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @PatchMapping("/{disciplineName}/add/teacher/substitute/off/course/{emailTeacher}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> addSubstituteTeacherOffCourseDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.addSubstituteTeacherOffCourseDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @PatchMapping("/{disciplineName}/remove/teacher/substitute/off/course/{emailTeacher}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<DisciplineResponseDto> removeSubstituteTeacherOffCourseDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.removeSubstituteTeacherOffCourseDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }
}
