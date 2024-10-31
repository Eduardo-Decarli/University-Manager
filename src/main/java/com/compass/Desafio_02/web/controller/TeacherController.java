package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Registration;
import com.compass.Desafio_02.entities.Teacher;
import com.compass.Desafio_02.services.TeacherService;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import com.compass.Desafio_02.web.dto.StudentResponseDto;
import com.compass.Desafio_02.web.dto.TeacherResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/teachers")
@Validated
public class TeacherController {

    private final TeacherService teacherService;

    // ROLE_COORDINATOR
    @PostMapping
    public ResponseEntity<Teacher> create(@Valid @RequestBody Teacher teacher) {
        Teacher response = teacherService.create(teacher);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getById(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> list() {
        List<Teacher> teachers = teacherService.list();
        return ResponseEntity.ok().body(teachers);
    }

    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody Teacher update) {
        teacherService.update(update);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        teacherService.remove(id);
        return ResponseEntity.noContent().build();
    }

    // ROLE_TEACHER
    @GetMapping("/me")
    public ResponseEntity<TeacherResponseDto> myData() {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        return ResponseEntity.ok().build();
    }

    @PutMapping("/me")
    public ResponseEntity<StudentResponseDto> myUpdate() {

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

    @GetMapping("/me/{nameDiscipline}/students")
    public ResponseEntity<List<StudentResponseDto>> myDisciplinesStudents() {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/course")
    public ResponseEntity<CourseResponseDto> myCourse() {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/course/registration")
    public ResponseEntity<Registration> courseRegistrations() {

        // TO-DO -> Acessar as proprias disciplinas
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/{nameDiscipline}/registration")
    public ResponseEntity<Registration> disciplinesRegistrations() {

        // TO-DO -> Acessar as proprias disciplinas
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        return ResponseEntity.ok().build();
    }
}
