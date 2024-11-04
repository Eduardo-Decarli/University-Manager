package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.jwt.JwtUserDetails;
import com.compass.Desafio_02.services.StudentService;
import com.compass.Desafio_02.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<StudentResponseDto> create(@Valid @RequestBody StudentCreateDto studentDto) {
        StudentResponseDto response = studentService.create(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<StudentResponseDto> getById(@PathVariable Long id) {
        StudentResponseDto response = studentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<List<StudentResponseDto>> list() {
        List<StudentResponseDto> responses = studentService.list();
        return ResponseEntity.ok().body(responses);
    }

    @PutMapping("/modification/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> update(@Valid @PathVariable Long id, @RequestBody StudentCreateDto studentDto) {
        studentService.update(id, studentDto);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        studentService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentResponseDto> myData(@AuthenticationPrincipal JwtUserDetails userDetails) {
        StudentResponseDto response = studentService.getById(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentResponseDto> myUpdate(@AuthenticationPrincipal JwtUserDetails userDetails, @RequestBody StudentCreateDto studentDto) {
        StudentResponseDto response = studentService.update(userDetails.getId(), studentDto);
        return ResponseEntity.status(204).body(response);
    }

    @GetMapping("/me/course")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<CourseResponseDto> myCourse(@AuthenticationPrincipal JwtUserDetails userDetails) {
        CourseResponseDto courseDto = studentService.getMyCourse(userDetails.getId());
        return ResponseEntity.ok().body(courseDto);
    }

    @GetMapping("/me/course/disciplines")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<DisciplineResponseDto>> myDisciplines(@AuthenticationPrincipal JwtUserDetails userDetails) {
        List<DisciplineResponseDto> responses = studentService.getStudentDisciplines(userDetails.getId());
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/me/registration")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<RegistrationResponseDto> myRegistration(@AuthenticationPrincipal JwtUserDetails userDetails) {
        RegistrationResponseDto responseDto = studentService.getRegistration(userDetails.getId());
        return ResponseEntity.ok().body(responseDto);
    }
}
