package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.repositories.StudentRepository;
import com.compass.Desafio_02.web.exception.EmptyListException;
import com.compass.Desafio_02.web.exception.EntityUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public Student getById(Long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: student not found")
        );
    }

    public Student create(Student student) {
        try {
            return studentRepository.save(student);
        } catch(DataIntegrityViolationException ex){
            throw new EntityUniqueViolationException(
                    String.format("Error: There is a student with email: %s already registered", student.getEmail())
            );
        }
    }

    public List<Student> list() {
        List<Student> students = studentRepository.findAll();
        if(students.isEmpty()){
            throw new EmptyListException("Error: There are no registered students");
        }
        return students;
    }

    public void update(Student update) {
        Student student = getById(update.getId());

        student.setFirstName(update.getFirstName());
        student.setLastName(update.getLastName());
        student.setEmail(update.getEmail());
        student.setBirthDate(update.getBirthDate());
        student.setPassword(update.getPassword());
        student.setRole(update.getRole());
        student.setAddress(update.getAddress());

        studentRepository.save(student);
    }

    public void remove(Long id) {
        getById(id);
        studentRepository.deleteById(id);
    }
}
