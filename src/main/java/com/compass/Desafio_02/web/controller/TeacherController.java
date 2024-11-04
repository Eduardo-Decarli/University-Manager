package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.jwt.JwtUserDetails;
import com.compass.Desafio_02.services.RegistrationServices;
import com.compass.Desafio_02.services.TeacherService;
import com.compass.Desafio_02.web.dto.*;
import com.compass.Desafio_02.web.exception.handler.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<TeacherResponseDto> create(@Valid @RequestBody TeacherCreateDto teacherDto) {
        TeacherResponseDto response = teacherService.create(teacherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<TeacherResponseDto> getById(@PathVariable Long id) {
        TeacherResponseDto response = teacherService.getById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<List<TeacherResponseDto>> list() {
        List<TeacherResponseDto> responses = teacherService.list();
        return ResponseEntity.ok().body(responses);
    }

    @PutMapping("/modification/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> update(@Valid @PathVariable Long id, @RequestBody TeacherCreateDto update) {
        teacherService.update(id, update);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> removeById(@PathVariable Long id) {
        teacherService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<TeacherResponseDto> myData(@AuthenticationPrincipal JwtUserDetails userDetails) {
        TeacherResponseDto response = teacherService.getById(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<TeacherResponseDto> myUpdate(@AuthenticationPrincipal JwtUserDetails userDetails, @RequestBody TeacherCreateDto update) {
        TeacherResponseDto response = teacherService.update(userDetails.getId(), update);
        return ResponseEntity.status(204).body(response);
    }

    @GetMapping("/me/disciplines")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<DisciplineResponseDto>> myDisciplines(@AuthenticationPrincipal JwtUserDetails userDetails) {
        List<DisciplineResponseDto> response = teacherService.getDisciplines(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/me/{nameDiscipline}/students")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByDiscipline(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable String nameDiscipline) {
        List<StudentResponseDto> responses = teacherService.getStudentsByDiscipline(userDetails.getId(), nameDiscipline);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/me/course")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CourseResponseDto> myCourse(@AuthenticationPrincipal JwtUserDetails userDetails) {
        CourseResponseDto response = teacherService.getMyCourse(userDetails.getId());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/me/add/{id}/course/{courseName}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<TeacherResponseDto> addCourse(@PathVariable Long id, @PathVariable String courseName) {
        TeacherResponseDto response = teacherService.addCourse(id, courseName);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/me/course/registrations")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<RegistrationResponseDto>> getAllRegistrationsByCourse(@AuthenticationPrincipal JwtUserDetails userDetails) {
        TeacherResponseDto teacherResponseDto = teacherService.getById(userDetails.getId());
        List<RegistrationResponseDto> responses = registrationServices.getRegistrationsByCourse(teacherResponseDto.getCourse());
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/me/{nameDiscipline}/registration")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<RegistrationResponseDto>> getAllRegistrationsByDiscipline(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable String nameDiscipline) {
        List<RegistrationResponseDto> responses = teacherService.getAllRegistrationsByDiscipline(userDetails.getId(), nameDiscipline);
        return ResponseEntity.ok().body(responses);
    }
}
