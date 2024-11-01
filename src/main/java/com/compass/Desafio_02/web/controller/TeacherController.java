package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.services.RegistrationServices;
import com.compass.Desafio_02.services.TeacherService;
import com.compass.Desafio_02.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/teacher")
@Validated
public class TeacherController {

    private final TeacherService teacherService;
    private final RegistrationServices registrationServices;

    @PostMapping
    public ResponseEntity<TeacherResponseDto> create(@Valid @RequestBody TeacherCreateDto teacherDto) {
        TeacherResponseDto response = teacherService.create(teacherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getById(@PathVariable Long id) {
        TeacherResponseDto response = teacherService.getById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> list() {
        List<TeacherResponseDto> responses = teacherService.list();
        return ResponseEntity.ok().body(responses);
    }

    @PutMapping("/modification/{id}")
    public ResponseEntity<Void> update(@Valid @PathVariable Long id, @RequestBody TeacherCreateDto update) {
        teacherService.update(id, update);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        teacherService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<TeacherResponseDto> myData(@PathVariable Long id) {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        TeacherResponseDto response = teacherService.getById(id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/me/{id}")
    public ResponseEntity<TeacherResponseDto> myUpdate(@Valid @PathVariable Long id, @RequestBody TeacherCreateDto update) {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        TeacherResponseDto response = teacherService.update(id, update);
        return ResponseEntity.status(204).body(response);
    }

    @GetMapping("/me/disciplines/{id}")
    public ResponseEntity<List<DisciplineResponseDto>> myDisciplines(@PathVariable Long id) {

        // TO-DO -> Acessar as proprias disciplinas
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        List<DisciplineResponseDto> response = teacherService.getDisciplines(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/me/{id}/{nameDiscipline}/students")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByDiscipline(@PathVariable Long id, @PathVariable String nameDiscipline) {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        List<StudentResponseDto> responses = teacherService.getStudentsByDiscipline(id, nameDiscipline);

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/me/{id}/course")
    public ResponseEntity<CourseResponseDto> myCourse(@PathVariable Long id) {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        CourseResponseDto response = teacherService.getMyCourse(id);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/me/{id}/course/registrations")
    public ResponseEntity<List<RegistrationResponseDto>> getAllRegistrationsByCourse(@PathVariable Long id) {

        // TO-DO -> Acessar as proprias disciplinas
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser
        TeacherResponseDto teacherResponseDto = teacherService.getById(id);
        List<RegistrationResponseDto> responses = registrationServices.getRegistrationsByCourse(teacherResponseDto.getCourse());

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/me/{id}/{nameDiscipline}/registration")
    public ResponseEntity<List<RegistrationResponseDto>> getAllRegistrationsByDiscipline(@PathVariable Long id, @PathVariable String nameDiscipline) {

        // TO-DO -> Acessar as proprias disciplinas
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        List<RegistrationResponseDto> responses = teacherService.getAllRegistrationsByDiscipline(id, nameDiscipline);
        return ResponseEntity.ok().body(responses);
    }
}
