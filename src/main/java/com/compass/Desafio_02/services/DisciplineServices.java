package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Discipline;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.repositories.DisciplineRepository;
import com.compass.Desafio_02.web.dto.DisciplineCreateDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import com.compass.Desafio_02.web.dto.mapper.DisciplineMapper;
import com.compass.Desafio_02.web.dto.mapper.StudentMapper;
import com.compass.Desafio_02.web.exception.EmptyListException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineServices {


    @Autowired
    private DisciplineRepository repository;
    private StudentService studentService;

    public DisciplineResponseDto createDiscipline(DisciplineCreateDto disciplineDto) {
        Discipline discipline = DisciplineMapper.toDiscipline(disciplineDto);
        repository.save(discipline);
        DisciplineResponseDto response = DisciplineMapper.toDto(discipline);
        return response;
    }

    public List<DisciplineResponseDto> getAllDisciplines() {
        List<Discipline> response = repository.findAll();
        if(response.isEmpty()){
            throw new EmptyListException("Error: There are no registered disciplines");
        }
        return DisciplineMapper.toListDto(response);
    }

    public DisciplineResponseDto getDisciplineById(Long id) {
        Discipline discipline = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Discipline not found")
        );
        return DisciplineMapper.toDto(discipline);
    }

    public Discipline getDisciplineByName(String name){
        return repository.findDisciplineByName(name);
    }

    public DisciplineResponseDto updateDiscipline(Long id, DisciplineCreateDto update) {
        Discipline discipline = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Discipline not found")
        );

        discipline.setName(update.getName());
        discipline.setDescription(update.getDescription());
        discipline.setMainTeacherEmail(update.getMainTeacherEmail());
        discipline.setSubsTeacherEmail(update.getSubsTeacherEmail());
        discipline.setStudents(update.getStudents());

        repository.save(discipline);
        DisciplineResponseDto responseDto = DisciplineMapper.toDto(discipline);
        return responseDto;
    }

    public void deleteDiscipline(long id) {
        getDisciplineById(id);
        repository.deleteById(id);
    }


    public DisciplineResponseDto addStudentInDisciplineByName(String disciplineName, Long id) {
        Discipline discipline = getDisciplineByName(disciplineName);
        List<Student> students = discipline.getStudents();
        Student student = StudentMapper.toStudent(studentService.getById(id));
        students.add(student);
        discipline.setStudents(students);
        repository.save(discipline);
        return DisciplineMapper.toDto(discipline);
    }

    public DisciplineResponseDto removeStudentInDisciplineByName(String disciplineName, Long id) {
        Discipline discipline = getDisciplineByName(disciplineName);
        List<Student> students = discipline.getStudents();
        Student student = StudentMapper.toStudent(studentService.getById(id));

        students.removeIf((x) -> x.getId().equals(student.getId()));

        discipline.setStudents(students);
        repository.save(discipline);
        return DisciplineMapper.toDto(discipline);
    }

    public DisciplineResponseDto addTitularTeacherByDiscipline(String disciplineName, String emailTeacher) {
        Discipline discipline = getDisciplineByName(disciplineName);
        discipline.setMainTeacherEmail(emailTeacher);
        repository.save(discipline);
        DisciplineResponseDto responseDto = DisciplineMapper.toDto(discipline);
        return responseDto;
    }

    public DisciplineResponseDto removeTitularTeacherDiscipline(String disciplineName, String emailTeacher) {
        Discipline discipline = getDisciplineByName(disciplineName);
        if(discipline.getMainTeacherEmail().equals(emailTeacher)){
            discipline.setMainTeacherEmail(null);
            repository.save(discipline);
        }
        DisciplineResponseDto responseDto = DisciplineMapper.toDto(discipline);
        return responseDto;
    }

    public DisciplineResponseDto addSubstituteTeacherDiscipline(String disciplineName, String emailTeacher) {
        Discipline discipline = getDisciplineByName(disciplineName);
        discipline.setSubsTeacherEmail(emailTeacher);
        repository.save(discipline);
        DisciplineResponseDto responseDto = DisciplineMapper.toDto(discipline);
        return responseDto;
    }

    public DisciplineResponseDto removeSubstituteTeacherDiscipline(String disciplineName, String emailTeacher) {
        Discipline discipline = getDisciplineByName(disciplineName);

        if(discipline.getSubsTeacherEmail().equals(emailTeacher)) {
            discipline.setSubsTeacherEmail(null);
            repository.save(discipline);
        }
        DisciplineResponseDto responseDto = DisciplineMapper.toDto(discipline);
        return responseDto;
    }
}
