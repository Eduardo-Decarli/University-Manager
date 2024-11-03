package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.*;
import com.compass.Desafio_02.repositories.CourseRepository;
import com.compass.Desafio_02.repositories.TeacherRepository;
import com.compass.Desafio_02.web.dto.*;
import com.compass.Desafio_02.web.dto.mapper.*;
import com.compass.Desafio_02.web.exception.CourseNotNullException;
import com.compass.Desafio_02.web.exception.EmptyListException;
import com.compass.Desafio_02.web.exception.EntityUniqueViolationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TeacherService {

    private final TeacherRepository repository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    public TeacherResponseDto create(TeacherCreateDto teacherDto) {
        try {
            Teacher teacher = TeacherMapper.toTeacher(teacherDto);
            teacher.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
            Teacher teacherSaved = repository.save(teacher);
            return TeacherMapper.toDto(teacherSaved);
        } catch(DataIntegrityViolationException ex){
            throw new EntityUniqueViolationException(
                    String.format("Error: There is a professor with email: %s already registered", teacherDto.getEmail())
            );
        }
    }

    public TeacherResponseDto getById(Long id) {
        Teacher teacher = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: teacher not found")
        );
        return TeacherMapper.toDto(teacher);
    }

    public List<TeacherResponseDto> list() {
        List<Teacher> response = repository.findAll();
        if(response.isEmpty()){
            throw new EmptyListException("Error: There are no registered professors");
        }
        return TeacherMapper.toListDto(response);
    }

    public TeacherResponseDto update(Long id, TeacherCreateDto teacherDto) {
        Teacher teacher = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: teacher not found")
        );

        teacher.setFirstName(teacherDto.getFirstName());
        teacher.setLastName(teacherDto.getLastName());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setBirthDate(teacherDto.getBirthDate());
        teacher.setPassword(teacherDto.getPassword());
        try{
            Teacher teacherSaved = repository.save(teacher);
            return TeacherMapper.toDto(teacherSaved);
        } catch(DataIntegrityViolationException ex) {
            throw new EntityUniqueViolationException(
                    String.format("Error: There is a professor with email: %s already registered", teacherDto.getEmail())
            );
        }
    }

    public void remove(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    public List<DisciplineResponseDto> getDisciplines(Long id){
        Teacher response = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: teacher not found")
        );

        List<DisciplineResponseDto> responseListDisciplines = new ArrayList<>();

        if(response.getMainTeacher() != null) {
            responseListDisciplines.add(DisciplineMapper.toDto(response.getMainTeacher()));
        }
        if(response.getSubsTeacher() != null) {
            responseListDisciplines.add(DisciplineMapper.toDto(response.getSubsTeacher()));
        }
        if(response.getSubsTeacherOffCourse() != null){
            responseListDisciplines.add(DisciplineMapper.toDto(response.getSubsTeacherOffCourse()));
        }
        if(responseListDisciplines.isEmpty()) {
            throw new EmptyListException("Error: There is no subject registered for this teacher");
        }
        return responseListDisciplines;
    }

    public List<StudentResponseDto> getStudentsByDiscipline(Long id, String name){
        Teacher teacher = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: teacher not found")
        );
        if(teacher.getMainTeacher() != null && name.equalsIgnoreCase(teacher.getMainTeacher().getName())) {
            List<Student> students = teacher.getMainTeacher().getStudents();
            return StudentMapper.toListDto(students);
        } else if(teacher.getSubsTeacher() != null && name.equalsIgnoreCase(teacher.getSubsTeacher().getName())) {
            List<Student> students = teacher.getSubsTeacher().getStudents();
            return StudentMapper.toListDto(students);
        } else if(teacher.getSubsTeacherOffCourse() != null && name.equalsIgnoreCase(teacher.getSubsTeacherOffCourse().getName())) {
            List<Student> students = teacher.getSubsTeacherOffCourse().getStudents();
            return StudentMapper.toListDto(students);
        }else{
            throw new EntityNotFoundException("Error: This discipline is invalid or not available");
        }
    }

    public Discipline getDisciplineByName(Long id, String name){
        Teacher teacher = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: teacher not found")
        );
        if(teacher.getMainTeacher() != null && name.equalsIgnoreCase(teacher.getMainTeacher().getName())) {
            return teacher.getMainTeacher();
        } else if(teacher.getSubsTeacher() != null && name.equalsIgnoreCase(teacher.getSubsTeacher().getName())) {
            return teacher.getSubsTeacher();
        } else if(teacher.getSubsTeacherOffCourse() != null && name.equalsIgnoreCase(teacher.getSubsTeacherOffCourse().getName())) {
            return teacher.getSubsTeacherOffCourse();
        }else{
            throw new EntityNotFoundException("Error: This discipline is invalid or not available");
        }
    }

    public CourseResponseDto getMyCourse(Long id) {
        TeacherResponseDto teacherDto = getById(id);
        if(teacherDto.getCourse() == null){
            throw new CourseNotNullException("Error: The specified course is not registered");
        }
        return CourseMapper.toDto(teacherDto.getCourse());
    }

    public List<RegistrationResponseDto> getAllRegistrationsByDiscipline(Long id, String nameDiscipline) {
        Discipline discipline = getDisciplineByName(id, nameDiscipline);
        List<StudentResponseDto> students = getStudentsByDiscipline(id, discipline.getName());
        if(students.isEmpty()){
            throw new EmptyListException("Error: There are no students for this discipline");
        }
        List<Registration> registrations = new ArrayList<>();
        for(StudentResponseDto student : students) {
            Registration registration = student.getRegistration();
            if (registration != null) {
                registrations.add(registration);
            }
        }
        return RegistrationMapper.toListDto(registrations);
    }

    public TeacherResponseDto addCourse(Long id, String courseName) {
        Course course = courseRepository.findCourseByName(courseName);
        Teacher teacher = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: teacher not found")
        );

        teacher.setCourse(course);
        Teacher teacherSaved = repository.save(teacher);
        return TeacherMapper.toDto(teacherSaved);
    }
}
