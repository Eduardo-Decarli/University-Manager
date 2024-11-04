package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.entities.api.Address;
import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.repositories.StudentRepository;
import com.compass.Desafio_02.web.controller.apiCep.AddressConsumerFeign;
import com.compass.Desafio_02.web.dto.*;
import com.compass.Desafio_02.web.dto.mapper.*;
import com.compass.Desafio_02.web.exception.EmptyListException;
import com.compass.Desafio_02.web.exception.EntityUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    @Autowired
    private AddressConsumerFeign addressConsumerFeign;
    
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public StudentResponseDto getById(Long id) {
        Student student = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: student not found")
        );
        return StudentMapper.toDto(student);
    }


    public StudentResponseDto create(StudentCreateDto studentDto) {

            Student student = StudentMapper.toStudent(studentDto);
            student.setRole(Role.ROLE_STUDENT);
            student.setPassword(passwordEncoder.encode(studentDto.getPassword()));

            Address address = addressConsumerFeign.getAddresByCep(studentDto.getAddress());
            String addressStudent = String.format(address.getEstado() + "/" + address.getUf() + " | " + address.getLocalidade() + ", " + address.getBairro() + ", " + address.getLogradouro());
            student.setAddress(addressStudent);
        try {
            repository.save(student);
            return StudentMapper.toDto(student);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityUniqueViolationException(
                    String.format("Error: There is a student with email: %s already registered", studentDto.getEmail())
            );
        }
    }

    public List<StudentResponseDto> list() {
        List<Student> response = repository.findAll();
        if (response.isEmpty()) {
            throw new EmptyListException("Error: There are no registered students");
        }
        return StudentMapper.toListDto(response);
    }

    public StudentResponseDto update(Long id, StudentCreateDto studentDto) {
        Student student = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: student not found")
        );

        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setBirthDate(studentDto.getBirthDate());
        student.setPassword(studentDto.getPassword());

        Address address = addressConsumerFeign.getAddresByCep(studentDto.getAddress());
        String addressStudent = String.format(address.getEstado() + "/" + address.getUf() + " | " + address.getLocalidade() + ", " + address.getBairro() + ", " + address.getLogradouro());
        student.setAddress(addressStudent);

        try {
            repository.save(student);
            return StudentMapper.toDto(student);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityUniqueViolationException(
                    String.format("Error: There is a student with email: %s already registered", studentDto.getEmail())
            );
        }
    }

    public void remove(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    public List<DisciplineResponseDto> getStudentDisciplines(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: student not found"));

        Course course = student.getRegistration().getCourse();
        if (course == null || course.getDisciplines().isEmpty()) {
            throw new EmptyListException("Error: No disciplines found for this student");
        }

        return DisciplineMapper.toListDto(course.getDisciplines());
    }

    public CourseResponseDto getMyCourse(Long id) {
        StudentResponseDto studentDto = getById(id);
        return CourseMapper.toDto(studentDto.getRegistration().getCourse());
    }

    public RegistrationResponseDto getRegistration(Long id) {
        StudentResponseDto studentDto = getById(id);
        return RegistrationMapper.toDto(studentDto.getRegistration());
    }
}
