package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Teacher;
import com.compass.Desafio_02.repositories.TeacherRepository;
import com.compass.Desafio_02.web.exception.EmptyListException;
import com.compass.Desafio_02.web.exception.EntityUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public Teacher getById(Long id) {
        return teacherRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: teacher not found")
        );
    }

    public Teacher create(Teacher teacher) {
        try {
            return teacherRepository.save(teacher);
        } catch(DataIntegrityViolationException ex){
            throw new EntityUniqueViolationException(
                    String.format("Error: There is a professor with email: %s already registered", teacher.getEmail())
            );
        }
    }

    public List<Teacher> list() {
        List<Teacher> teachers = teacherRepository.findAll();
        if(teachers.isEmpty()){
            throw new EmptyListException("Error: There are no registered professors");
        }
        return teachers;
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
        getById(id);
        teacherRepository.deleteById(id);
    }
}
