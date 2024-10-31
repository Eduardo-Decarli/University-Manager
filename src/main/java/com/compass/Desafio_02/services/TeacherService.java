package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Teacher;
import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.repositories.TeacherRepository;
import com.compass.Desafio_02.web.dto.TeacherCreateDto;
import com.compass.Desafio_02.web.dto.TeacherResponseDto;
import com.compass.Desafio_02.web.dto.mapper.TeacherMapper;
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

    private final TeacherRepository repository;

    public TeacherResponseDto create(TeacherCreateDto teacherDto) {
        try {
            Teacher teacher = TeacherMapper.toTeacher(teacherDto);
            Teacher teacherSaved = repository.save(teacher);
            return TeacherMapper.toDto(teacherSaved);
        } catch(DataIntegrityViolationException ex){
            throw new EntityUniqueViolationException(
                    String.format("Error: There is a professor with email: %s already registered", teacherDto.getEmail())
            );
        }
    }

    public Teacher getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: teacher not found")
        );
    }

    public List<Teacher> list() {
        List<Teacher> response = repository.findAll();
        if(response.isEmpty()){
            throw new EmptyListException("Error: There are no registered professors");
        }
        return response;
    }

    public void update(Teacher update) {
        Teacher teacher = getById(update.getId());

        teacher.setFirstName(update.getFirstName());
        teacher.setLastName(update.getLastName());
        teacher.setEmail(update.getEmail());
        teacher.setBirthDate(update.getBirthDate());
        teacher.setPassword(update.getPassword());
        teacher.setRole(update.getRole());

        repository.save(teacher);
    }

    public void remove(Long id) {
        getById(id);
        repository.deleteById(id);
    }
}
