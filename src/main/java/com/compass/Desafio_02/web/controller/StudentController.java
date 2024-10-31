package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Discipline;
import com.compass.Desafio_02.entities.Registration;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.services.StudentService;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import com.compass.Desafio_02.web.dto.StudentResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/students")
@Validated
public class StudentController {

    private final StudentService studentService;

    // ROLE_COORDINATOR
    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
        Student response = studentService.create(student);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        Student student = studentService.getById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> list() {
        List<Student> students = studentService.list();
        return ResponseEntity.ok().body(students);
    }

    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody Student update) {
        studentService.update(update);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        studentService.remove(id);
        return ResponseEntity.noContent().build();
    }

    // ROLE_STUDENT
    @GetMapping("/me")
    public ResponseEntity<StudentResponseDto> myData() {

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

    @GetMapping("/me/course")
    public ResponseEntity<CourseResponseDto> myCourse() {

        // TO-DO -> Acessar os proprios dados
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/course/disciplines")
    public ResponseEntity<List<DisciplineResponseDto>> myDisciplines() {

        // TO-DO -> Acessar as proprias disciplinas
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/registration")
    public ResponseEntity<Registration> myRegistration() {

        // TO-DO -> Acessar as proprias disciplinas
        // Esse endpoint irá retornar as informações do usuario logado, depende da implementação da autenticação JWT
        // porem caso queira implementar usando um parametro id temporariamente pode ser

        return ResponseEntity.ok().build();
    }
}
