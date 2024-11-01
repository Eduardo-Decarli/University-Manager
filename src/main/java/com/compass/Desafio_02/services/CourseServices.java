package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Discipline;
import com.compass.Desafio_02.repositories.CoordinatorRepository;
import com.compass.Desafio_02.repositories.CourseRepository;
import com.compass.Desafio_02.web.dto.CourseCreateDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import com.compass.Desafio_02.web.dto.mapper.CourseMapper;
import com.compass.Desafio_02.web.exception.CoordinatorInCourseUniqueViolationException;
import com.compass.Desafio_02.web.exception.EmptyListException;
import com.compass.Desafio_02.web.exception.EntityUniqueViolationException;
import com.compass.Desafio_02.web.exception.QuantityDisciplinesViolationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServices {

    @Autowired
    private CourseRepository repository;
    private CoordinatorRepository coordinatorRepository;
    private DisciplineServices disciplineServices;

    public CourseResponseDto createCourse(CourseCreateDto courseDto) {
        try {
            Course course = CourseMapper.toCourse(courseDto);
            repository.save(course);
            return CourseMapper.toDto(course);
        } catch(DataIntegrityViolationException ex){
            throw new EntityUniqueViolationException(
                    String.format("Error: There is a course with this name: %s already registered", courseDto.getName())
            );
        }
    }

    public List<CourseResponseDto> getAllCourses() {
        List<Course> response = repository.findAll();
        if(response.isEmpty()){
            throw new EmptyListException("Error: There are no registered course");
        }
        return CourseMapper.toListDto(response);
    }

    public CourseResponseDto getCourseById(Long id) {
        Course course = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Course not found")
        );
        return CourseMapper.toDto(course);
    }

    public Course getCourseByName(String name) {
        return repository.findCourseByName(name);
    }

    public CourseResponseDto updateCourse(Long id, CourseCreateDto update) {
        Course course = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Course not found")
        );

        course.setName(update.getName());
        course.setDescription(update.getDescription());
        course.setCoordinator(update.getCoordinator());
        course.setDisciplines(update.getDisciplines());

        repository.save(course);
        return CourseMapper.toDto(course);
    }

    public void deleteCourse(long id) {
        getCourseById(id);
        repository.deleteById(id);
    }


    public CourseResponseDto addDiscipline(String courseName, String disciplineName) {
        Discipline discipline = disciplineServices.getDisciplineByName(disciplineName);
        Course course = getCourseByName(courseName);
        List<Discipline> disciplinesInCourse = course.getDisciplines();

        if(disciplinesInCourse.size() > 5) {
            throw new QuantityDisciplinesViolationException("Error: The maximum number of disciplines for this course has been exceeded");
        }

        disciplinesInCourse.add(discipline);
        course.setDisciplines(disciplinesInCourse);
        repository.save(course);
        return CourseMapper.toDto(course);
    }

    public CourseResponseDto removeDiscipline(String courseName, String disciplineName) {
        Discipline discipline = disciplineServices.getDisciplineByName(disciplineName);
        Course course = getCourseByName(courseName);
        List<Discipline> disciplinesInCourse = course.getDisciplines();

        disciplinesInCourse.removeIf((x) -> x.getName().equals(discipline.getName()));
        course.setDisciplines(disciplinesInCourse);
        repository.save(course);
        return CourseMapper.toDto(course);
    }

    public CourseResponseDto changeCoordinator(String courseName, Long idCoordinator) {
        Coordinator coordinator = coordinatorRepository.findById(idCoordinator).orElseThrow(
                () -> new EntityNotFoundException("Error: Coordinator not found")
        );
        Course course = getCourseByName(courseName);
        if(course.getCoordinator().getEmail().equals(coordinator.getEmail())){
            throw new CoordinatorInCourseUniqueViolationException("Error: This coordinator is already coordinating the course");
        }
        course.setCoordinator(coordinator);
        repository.save(course);
        return CourseMapper.toDto(course);
    }
}
