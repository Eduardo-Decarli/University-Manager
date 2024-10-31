package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.services.StudentService;
import com.compass.Desafio_02.web.dto.StudentCreateDto;
import com.compass.Desafio_02.web.dto.StudentResponseDto;
import com.compass.Desafio_02.web.dto.TeacherResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    @PostMapping
    public ResponseEntity<StudentResponseDto> create(@Valid @RequestBody StudentCreateDto studentDto) {
        StudentResponseDto response = studentService.create(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getById(@PathVariable Long id) {
        StudentResponseDto response = studentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> list() {
        List<StudentResponseDto> responses = studentService.list();
        return ResponseEntity.ok().body(responses);
    }


    @PutMapping("/modification/{id}")
    public ResponseEntity<Void> update(@Valid @PathVariable Long id, @RequestBody StudentCreateDto studentDto) {
        studentService.update(id, studentDto);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        studentService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
