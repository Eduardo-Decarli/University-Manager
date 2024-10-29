package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public Student getById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("student not found."));
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> list() {
        return studentRepository.findAll();
    }

    public void update(Student update) {
        Student student = getById(update.getId());

        student.setFirstName(update.getFirstName());
        student.setLastName(update.getLastName());
        student.setEmail(update.getEmail());
        student.setDateOfBirth(update.getDateOfBirth());
        student.setPassword(update.getPassword());
        student.setRole(update.getRole());
        student.setAddress(update.getAddress());

        studentRepository.save(student);
    }

    public void remove(Long id) {
        studentRepository.deleteById(id);
    }
}
