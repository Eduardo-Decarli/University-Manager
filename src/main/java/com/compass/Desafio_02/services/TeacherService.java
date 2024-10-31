package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Discipline;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.entities.Teacher;
import com.compass.Desafio_02.repositories.TeacherRepository;
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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public void update(Long id, TeacherCreateDto teacherDto) {
        Teacher teacher = TeacherMapper.toTeacher(getById(id));

        teacher.setFirstName(teacherDto.getFirstName());
        teacher.setLastName(teacherDto.getLastName());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setBirthDate(teacherDto.getBirthDate());
        teacher.setPassword(teacherDto.getPassword());

        repository.save(teacher);
    }

    public void remove(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    public List<DisciplineResponseDto> getDisciplines(Long id){
        Teacher response = TeacherMapper.toTeacher(getById(id));
        List<DisciplineResponseDto> responseListDisciplines = new ArrayList<>();
        responseListDisciplines.add(DisciplineMapper.toDto(response.getMainTeacher()));
        responseListDisciplines.add(DisciplineMapper.toDto(response.getSubsTeacher()));
        responseListDisciplines.add(DisciplineMapper.toDto(response.getSubsTeacherOffCourse()));
        return responseListDisciplines;
    }

    public List<StudentResponseDto> getStudentsByDiscipline(Long id, String name){
        Teacher teacher = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: teacher not found")
        );
        if(name.equals(teacher.getMainTeacher())) {
            List<Student> students = teacher.getMainTeacher().getStudents();
            return StudentMapper.toListDto(students);
        } else if(name.equals(teacher.getSubsTeacher())) {
            List<Student> students = teacher.getSubsTeacher().getStudents();
            return StudentMapper.toListDto(students);
        } else if(name.equals(teacher.getSubsTeacherOffCourse())) {
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
        if(name.equals(teacher.getMainTeacher())) {
            return teacher.getMainTeacher();
        } else if(name.equals(teacher.getSubsTeacher())) {
            return teacher.getSubsTeacher();
        } else if(name.equals(teacher.getSubsTeacherOffCourse())) {
            return teacher.getSubsTeacherOffCourse();
        }else{
            throw new EntityNotFoundException("Error: This discipline is invalid or not available");
        }
    }

    public CourseResponseDto getMyCourse(Long id) {
        TeacherResponseDto teacherDto = getById(id);
        return CourseMapper.toDto(teacherDto.getCourse());
    }
}
