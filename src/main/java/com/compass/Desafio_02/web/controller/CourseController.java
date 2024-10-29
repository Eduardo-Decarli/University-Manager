package com.compass.Desafio_02.web.controller;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.services.CourseServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    private final CourseServices services;

    public CourseController(CourseServices services){
        this.services = services;
    }

    @GetMapping("/course")
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
    public ResponseEntity<Course> updateCourse(@PathVariable long id, @RequestBody Course course) throws Exception {
        Course updatedCourse = services.updateCourse(id, course);
        return ResponseEntity.ok().body(updatedCourse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable long id) throws Exception {
        services.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
