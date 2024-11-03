package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Registration;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.repositories.CourseRepository;
import com.compass.Desafio_02.repositories.RegistrationRepository;
import com.compass.Desafio_02.repositories.StudentRepository;
import com.compass.Desafio_02.web.dto.RegistrationCreateDto;
import com.compass.Desafio_02.web.dto.RegistrationResponseDto;
import com.compass.Desafio_02.web.dto.mapper.RegistrationMapper;
import com.compass.Desafio_02.web.exception.EmptyListException;
import com.compass.Desafio_02.web.exception.StudentNotAgeException;
import com.compass.Desafio_02.web.exception.UniqueCourseViolationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

@Service
public class RegistrationServices {

    private final RegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public RegistrationServices(RegistrationRepository registrationRepository,
                                StudentRepository studentRepository,
                                CourseRepository courseRepository) {
        this.registrationRepository = registrationRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public RegistrationResponseDto getRegistrationById(Long id) {
        Registration registration = registrationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Registration not found")
        );
        return RegistrationMapper.toDto(registration);
    }

    public RegistrationResponseDto createRegistration(RegistrationCreateDto registrationDto) {
        Student student = studentRepository.findById(registrationDto.getStudentId()).orElseThrow(
                () -> new EntityNotFoundException("Error: Student not found")
        );
        Course course = courseRepository.findById(registrationDto.getCourseId()).orElseThrow(
                () -> new EntityNotFoundException("Error: Course not found")
        );
        if(student.getBirthDate().isAfter(LocalDate.now().minusYears(18))) {
            throw new StudentNotAgeException("Error: The student must be at least 18 years old to create a registration");
        }
        if(student.getRegistration() != null) {
            throw new UniqueCourseViolationException("Error: The student can be associated with only one course at a time");
        }
        Registration registration = new Registration();
        registration.setStudent(student);
        registration.setCourse(course);

        studentRepository.save(student);
        registrationRepository.save(registration);

        return RegistrationMapper.toDto(registration);
    }

    public List<RegistrationResponseDto> getAllRegistrations() {
        List<Registration> response = registrationRepository.findAll();
        if(response.isEmpty()){
            throw new EmptyListException("Error: There are no registered registrations");
        }
        return RegistrationMapper.toListDto(response);
    }

    public RegistrationResponseDto updateRegistration(Long id, RegistrationCreateDto update) {
       Registration registration = registrationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Registration not found")
        );

        Student student = studentRepository.findById(update.getStudentId()).orElseThrow(
                () -> new EntityNotFoundException("Error: Student not found")
        );
        Course course = courseRepository.findById(update.getCourseId()).orElseThrow(
                () -> new EntityNotFoundException("Error: Course not found")
        );

        registration.setCourse(course);
        registration.setStudent(student);

        registrationRepository.save(registration);
        return RegistrationMapper.toDto(registration);
    }

    public void deleteRegistration(long id) {
        getRegistrationById(id);
        registrationRepository.deleteById(id);
    }

    public List<RegistrationResponseDto> getRegistrationsByCourse(Course course) {
        List<Registration> responses = registrationRepository.findByCourse(course);
        if(responses.isEmpty()) {
            throw new EmptyListException("Error: Does not have Registrations in this course");
        }
        return RegistrationMapper.toListDto(responses);
    }
}
