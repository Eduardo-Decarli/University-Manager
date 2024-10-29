package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServices {

    @Autowired
    private CourseRepository repository;

    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    public Course getCourseById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(
                () -> new Exception("Course not found")
        );
    }

    public Course createCourse(Course course) throws Exception {
        if(course.getCoordinator().getCourse() != null){
            throw new Exception("Error: This coordinator already has a course in progress");
        }
        return repository.save(course);
    }

    public Course updateCourse(long id, Course newCourse) throws Exception {
        Course course = getCourseById(id);

        course.setName(newCourse.getName());
        course.setDescription(newCourse.getDescription());
        course.setCoordinator(newCourse.getCoordinator());
        course.setDisciplines(newCourse.getDisciplines());
        return repository.save(course);
    }

    public void deleteCourse(long id) throws Exception {
        repository.deleteById(id);
    }
}
