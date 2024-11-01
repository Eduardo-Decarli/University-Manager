package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.services.DisciplineServices;
import com.compass.Desafio_02.web.dto.DisciplineCreateDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DisciplineResponseDto> create(@Valid @RequestBody DisciplineCreateDto discipline) {
        DisciplineResponseDto response = services.createDiscipline(discipline);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/disciplines")
    public ResponseEntity<List<DisciplineResponseDto>> getAllRDisciplines(){
        List<DisciplineResponseDto> disciplines = services.getAllDisciplines();
        return ResponseEntity.ok().body(disciplines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineResponseDto> getDisciplineById(@PathVariable long id){
        DisciplineResponseDto discipline = services.getDisciplineById(id);
        return ResponseEntity.ok().body(discipline);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplineResponseDto> updateDiscipline(@Valid @PathVariable Long id, @RequestBody DisciplineCreateDto discipline) {
        DisciplineResponseDto update = services.updateDiscipline(id, discipline);
        return ResponseEntity.ok().body(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplineById(@PathVariable long id) {
        services.deleteDiscipline(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{disciplineName}/add/student/{id}")
    public ResponseEntity<DisciplineResponseDto> addStudentToDiscipline(@Valid @PathVariable String disciplineName, @PathVariable Long id) {
        DisciplineResponseDto discipline = services.addStudentInDisciplineByName(disciplineName, id);
        return ResponseEntity.ok().body(discipline);
    }

    @PatchMapping("/{disciplineName}/remove/student/{id}")
    public ResponseEntity<DisciplineResponseDto> removeStudentDiscipline(@Valid @RequestBody String disciplineName, @PathVariable Long id) {
        DisciplineResponseDto discipline = services.removeStudentInDisciplineByName(disciplineName, id);
        return ResponseEntity.ok().body(discipline);
    }

    @PatchMapping("/{disciplineName}/add/teacher/titular/{email}")
    public ResponseEntity<DisciplineResponseDto> addTitularTeacherDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.addTitularTeacherByDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @PatchMapping("/{disciplineName}/remove/teacher/titular/{email}")
    public ResponseEntity<DisciplineResponseDto> removeTitularTeacherDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.removeTitularTeacherDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @PatchMapping("/{disciplineName}/add/teacher/substitute/{email}")
    public ResponseEntity<DisciplineResponseDto> addSubstituteTeacherDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.addSubstituteTeacherDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }

    @PatchMapping("/{disciplineName}/remove/teacher/substitute/{email}")
    public ResponseEntity<DisciplineResponseDto> removeSubstituteTeacherDiscipline(@Valid @PathVariable String disciplineName, @PathVariable String emailTeacher) {
        DisciplineResponseDto update = services.removeSubstituteTeacherDiscipline(disciplineName, emailTeacher);
        return ResponseEntity.ok().body(update);
    }
}
