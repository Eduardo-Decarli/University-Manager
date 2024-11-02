package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.services.CourseServices;
import com.compass.Desafio_02.web.dto.CourseCreateDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CourseResponseDto> create(@Valid @RequestBody CourseCreateDto course) {
        CourseResponseDto response = services.createCourse(course);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAllCourses(){
        List<CourseResponseDto> courses = services.getAllCourses();
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable long id) {
        CourseResponseDto course = services.getCourseById(id);
        return ResponseEntity.ok().body(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(@Valid @PathVariable Long id, @RequestBody CourseCreateDto course) {
        CourseResponseDto updatedCourse = services.updateCourse(id, course);
        return ResponseEntity.ok().body(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable long id) throws Exception {
        services.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{courseName}/add/disciplines/{discipline}")
    public ResponseEntity<CourseResponseDto> addDiscipline(@PathVariable String courseName, @PathVariable String discipline) {
        CourseResponseDto response = services.addDiscipline(courseName, discipline);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{courseName}/remove/disciplines/{name}")
    public ResponseEntity<CourseResponseDto> removeDiscipline(@PathVariable String courseName, @PathVariable String discipline) {
        CourseResponseDto response = services.removeDiscipline(courseName, discipline);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{courseName}/change/coordinators/{id}")
    public ResponseEntity<CourseResponseDto> changeCoordinator(@PathVariable String courseName, @PathVariable Long idCoordinator) {
        CourseResponseDto update = services.changeCoordinator(courseName, idCoordinator);
        return ResponseEntity.ok().body(update);
    }
}
