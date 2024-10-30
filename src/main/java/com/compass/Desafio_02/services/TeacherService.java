package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.entities.Teacher;
import com.compass.Desafio_02.repositories.StudentRepository;
import com.compass.Desafio_02.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public Teacher getById(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("teacher not found."));
    }

    public Teacher create(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public List<Teacher> list() {
        return teacherRepository.findAll();
    }

    public void update(Teacher update) {
        Teacher teacher = getById(update.getId());

        teacher.setFirstName(update.getFirstName());
        teacher.setLastName(update.getLastName());
        teacher.setEmail(update.getEmail());
        teacher.setBirthDate(update.getBirthDate());
        teacher.setPassword(update.getPassword());
        teacher.setRole(update.getRole());

        teacherRepository.save(teacher);
    }

    public void remove(Long id) {
        teacherRepository.deleteById(id);
    }
}
