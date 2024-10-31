package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Teacher;
import com.compass.Desafio_02.services.TeacherService;
import com.compass.Desafio_02.web.dto.TeacherCreateDto;
import com.compass.Desafio_02.web.dto.TeacherResponseDto;
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

    @PostMapping
    public ResponseEntity<TeacherResponseDto> create(@Valid @RequestBody TeacherCreateDto teacherDto) {
        TeacherResponseDto response = teacherService.create(teacherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
}
