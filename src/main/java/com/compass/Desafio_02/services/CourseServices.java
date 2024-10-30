package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.repositories.CourseRepository;
import com.compass.Desafio_02.web.exception.EmptyListException;
import com.compass.Desafio_02.web.exception.EntityUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServices {

    @Autowired
    private CourseRepository repository;

    public Course getCourseById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Course not found")
        );
    }

    public Course createCourse(Course course) {
        try {
            return repository.save(course);
        } catch(DataIntegrityViolationException ex){
            throw new EntityUniqueViolationException(
                    String.format("Error: There is a course with this name: %s already registered", course.getName())
            );
        }
    }

    public List<Course> getAllCourses() {
        List<Course> courses = repository.findAll();
        if(courses.isEmpty()){
            throw new EmptyListException("Error: There are no registered course");
        }
        return courses;
    }

    public Course updateCourse(Course update) {
        Course course = getCourseById(update.getId());

        course.setName(update.getName());
        course.setDescription(update.getDescription());
        course.setCoordinator(update.getCoordinator());
        course.setDisciplines(update.getDisciplines());

        return repository.save(course);
    }

    public void deleteCourse(long id) {
        getCourseById(id);
        repository.deleteById(id);
    }
}
