package com.compass.Desafio_02.web.controller;
import com.compass.Desafio_02.services.CourseServices;
import com.compass.Desafio_02.web.dto.CourseCreateDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@Validated
public class CourseController {

    private final CourseServices services;

    public CourseController(CourseServices services){
        this.services = services;
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CourseResponseDto> create(@Valid @RequestBody CourseCreateDto course) {
        CourseResponseDto response = services.createCourse(course);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('COORDINATOR', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses(){
        List<CourseResponseDto> courses = services.getAllCourses();
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable long id) {
        CourseResponseDto course = services.getCourseById(id);
        return ResponseEntity.ok().body(course);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CourseResponseDto> updateCourse(@Valid @PathVariable Long id, @RequestBody CourseCreateDto course) {
        CourseResponseDto updatedCourse = services.updateCourse(id, course);
        return ResponseEntity.ok().body(updatedCourse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<Void> deleteCourseById(@PathVariable long id) {
        services.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{courseName}/add/disciplines/{discipline}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CourseResponseDto> addDiscipline(@PathVariable String courseName, @PathVariable String discipline) {
        CourseResponseDto response = services.addDiscipline(courseName, discipline);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{courseName}/remove/disciplines/{discipline}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CourseResponseDto> removeDiscipline(@PathVariable String courseName, @PathVariable String discipline) {
        CourseResponseDto response = services.removeDiscipline(courseName, discipline);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{courseName}/change/coordinators/{id}")
    @PreAuthorize("hasRole('COORDINATOR')")
    public ResponseEntity<CourseResponseDto> changeCoordinator(@PathVariable String courseName, @PathVariable Long id) {
        CourseResponseDto update = services.changeCoordinator(courseName, id);
        return ResponseEntity.ok().body(update);
    }
}
