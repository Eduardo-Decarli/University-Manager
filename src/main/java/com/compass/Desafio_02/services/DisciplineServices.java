package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Discipline;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.repositories.DisciplineRepository;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import com.compass.Desafio_02.web.dto.mapper.DisciplineMapper;
import com.compass.Desafio_02.web.dto.mapper.StudentMapper;
import com.compass.Desafio_02.web.exception.EmptyListException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineServices {


    @Autowired
    private DisciplineRepository repository;
    private StudentService studentService;

    public Discipline getDisciplineById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Discipline not found")
        );
    }

    public Discipline getDisciplineByName(String name){
        return repository.findDisciplineByName(name);
    }

    public Discipline createDiscipline(Discipline discipline) {
        return repository.save(discipline);
    }

    public List<Discipline> getAllDisciplines() {
        List<Discipline> response = repository.findAll();
        if(response.isEmpty()){
            throw new EmptyListException("Error: There are no registered disciplines");
        }
        return response;
    }

    public Discipline updateDiscipline(Discipline update) {
        Discipline discipline = getDisciplineById(update.getId());

        discipline.setName(update.getName());
        discipline.setDescription(update.getDescription());
        discipline.setMainTeacherEmail(update.getMainTeacherEmail());
        discipline.setSubsTeacherEmail(update.getSubsTeacherEmail());
        discipline.setStudents(update.getStudents());

        return repository.save(discipline);
    }

    public void deleteDiscipline(long id) {
        getDisciplineById(id);
        repository.deleteById(id);
    }


    public DisciplineResponseDto addStudentInDisciplineByName(String disciplineName, Long id) {
        Discipline discipline = getDisciplineByName(disciplineName);
        List<Student> students = discipline.getStudents();
        Student studant = StudentMapper.toStudent(studentService.getById(id));
        students.add(studant);
        discipline.setStudents(students);
        updateDiscipline(discipline);
        return DisciplineMapper.toDto(discipline);
    }

    public DisciplineResponseDto removeStudentInDisciplineByName(String disciplineName, Long id) {
        Discipline discipline = getDisciplineByName(disciplineName);
        List<Student> students = discipline.getStudents();
        Student studant = StudentMapper.toStudent(studentService.getById(id));
        students.remove(studant);
        discipline.setStudents(students);
        updateDiscipline(discipline);
        return DisciplineMapper.toDto(discipline);
    }
}
