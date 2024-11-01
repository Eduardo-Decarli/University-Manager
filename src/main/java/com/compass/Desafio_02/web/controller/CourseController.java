package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.services.CourseServices;
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
    public ResponseEntity<Course> create(@Valid @RequestBody Course course) {
        Course response = services.createCourse(course);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(){
        List<Course> courses = services.getAllCourses();
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable long id) throws Exception {
        Course course = services.getCourseById(id);
        return ResponseEntity.ok().body(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@Valid @RequestBody Course course) throws Exception {
        Course updatedCourse = services.updateCourse(course);
        return ResponseEntity.ok().body(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable long id) throws Exception {
        services.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{courseName}/add/discipline/{name}")
    public ResponseEntity<CourseResponseDto> addDiscipline(@PathVariable String courseName, @PathVariable String discipline) {
        CourseResponseDto response = services.addDiscipline(courseName, discipline);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{courseName}/remove/discipline/{name}")
    public ResponseEntity<CourseResponseDto> removeDiscipline(@PathVariable String courseName, @PathVariable String discipline) {
        CourseResponseDto response = services.removeDiscipline(courseName, discipline);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{courseName}/change/coordinator/{id}")
    public ResponseEntity<CourseResponseDto> changeCoordinator(@PathVariable String courseName, @PathVariable Long idCoordinator) {
        CourseResponseDto update = services.changeCoordinator(courseName, idCoordinator);
        return ResponseEntity.ok().body(update);
    }
}
