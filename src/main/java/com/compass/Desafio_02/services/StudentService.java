package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.entities.Teacher;
import com.compass.Desafio_02.repositories.StudentRepository;
import com.compass.Desafio_02.web.dto.*;
import com.compass.Desafio_02.web.dto.mapper.CourseMapper;
import com.compass.Desafio_02.web.dto.mapper.DisciplineMapper;
import com.compass.Desafio_02.web.dto.mapper.StudentMapper;
import com.compass.Desafio_02.web.dto.mapper.TeacherMapper;
import com.compass.Desafio_02.web.exception.EmptyListException;
import com.compass.Desafio_02.web.exception.EntityUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentResponseDto getById(Long id) {
        Student student = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: student not found")
        );
        return StudentMapper.toDto(student);
    }


    public StudentResponseDto create(StudentCreateDto studentDto) {
        try {
            Student student = StudentMapper.toStudent(studentDto);
            Student studentSaved = repository.save(student);
            return StudentMapper.toDto(studentSaved);
        } catch(DataIntegrityViolationException ex){
            throw new EntityUniqueViolationException(
                    String.format("Error: There is a student with email: %s already registered", studentDto.getEmail())
            );
        }
    }

    public List<StudentResponseDto> list() {
        List<Student> response = repository.findAll();
        if(response.isEmpty()){
            throw new EmptyListException("Error: There are no registered students");
        }
        return StudentMapper.toListDto(response);
    }

    public void update(Long id, StudentCreateDto studentDto) {
        Student student = StudentMapper.toStudent(getById(id));

        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setBirthDate(studentDto.getDateOfBirth());
        student.setPassword(studentDto.getPassword());
        student.setRole(studentDto.getRole());
        student.setAddress(studentDto.getAddress());

        repository.save(student);
    }


    public void remove(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    public List<DisciplineResponseDto> getStudentDisciplines(Long id){
        Student student = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: student not found"));

        Course course = student.getCourse();
        if (course == null || course.getDisciplines().isEmpty()) {
            throw new EmptyListException("Error: No disciplines found for this student");
        }

        return DisciplineMapper.toListDto(course.getDisciplines());
    }

    public CourseResponseDto getMyCourse(Long id){
        StudentResponseDto studentDto = getById(id);
        return CourseMapper.toDto(studentDto.getCourse());
    }
}
