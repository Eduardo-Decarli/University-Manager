package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.entities.Discipline;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.entities.Teacher;
import com.compass.Desafio_02.repositories.CoordinatorRepository;
import com.compass.Desafio_02.repositories.DisciplineRepository;
import com.compass.Desafio_02.repositories.StudentRepository;
import com.compass.Desafio_02.repositories.TeacherRepository;
import com.compass.Desafio_02.web.dto.DisciplineCreateDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import com.compass.Desafio_02.web.dto.mapper.DisciplineMapper;
import com.compass.Desafio_02.web.dto.mapper.StudentMapper;
import com.compass.Desafio_02.web.exception.EmptyListException;
import com.compass.Desafio_02.web.exception.InvalidStudentEmailException;
import com.compass.Desafio_02.web.exception.InvalidTeacherEmailException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplineServices {


    @Autowired
    private final DisciplineRepository disciplineRepository;
    private final StudentService studentService;
    private final CoordinatorRepository coordinatorRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public DisciplineServices(DisciplineRepository disciplineRepository, StudentService studentService, CoordinatorRepository coordinatorRepository, TeacherRepository teacherRepository,
                              StudentRepository studentRepository) {
        this.disciplineRepository = disciplineRepository;
        this.studentService = studentService;
        this.coordinatorRepository = coordinatorRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public DisciplineResponseDto createDiscipline(DisciplineCreateDto disciplineDto) {
        Discipline discipline = DisciplineMapper.toDiscipline(disciplineDto);
        disciplineRepository.save(discipline);
        DisciplineResponseDto response = DisciplineMapper.toDto(discipline);
        return response;
    }

    public List<DisciplineResponseDto> getAllDisciplines() {
        List<Discipline> response = disciplineRepository.findAll();
        if(response.isEmpty()){
            throw new EmptyListException("Error: There are no registered disciplines");
        }
        return DisciplineMapper.toListDto(response);
    }

    public DisciplineResponseDto getDisciplineById(Long id) {
        Discipline discipline = disciplineRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Discipline not found")
        );
        return DisciplineMapper.toDto(discipline);
    }

    public Discipline getDisciplineByName(String name){
        Discipline discipline = disciplineRepository.findDisciplineByName(name);
        if(discipline == null) {
            throw new EntityNotFoundException("Error: Discipline not found");
        }
        return discipline;
    }

    public DisciplineResponseDto updateDiscipline(Long id, DisciplineCreateDto update) {
        Discipline discipline = disciplineRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Discipline not found")
        );

        discipline.setName(update.getName());
        discipline.setDescription(update.getDescription());

        disciplineRepository.save(discipline);
        return DisciplineMapper.toDto(discipline);
    }

    public void deleteDiscipline(long id) {
        getDisciplineById(id);
        disciplineRepository.deleteById(id);
    }


    public DisciplineResponseDto addStudentInDisciplineByName(String disciplineName, Long id) {
        Discipline discipline = getDisciplineByName(disciplineName);
        List<Student> students = discipline.getStudents();
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: student not found")
        );
        for(Student correntStudent : students){
            if(correntStudent.getEmail().equalsIgnoreCase(student.getEmail())) {
                throw new InvalidStudentEmailException("The student is already registered for the discipline");
            }
        }
        students.add(student);
        discipline.setStudents(students);
        disciplineRepository.save(discipline);
        return DisciplineMapper.toDto(discipline);
    }

    public DisciplineResponseDto removeStudentInDisciplineByName(String disciplineName, Long id) {
        Discipline discipline = getDisciplineByName(disciplineName);
        List<Student> students = discipline.getStudents();
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: student not found")
        );

        students.removeIf((x) -> x.getId().equals(student.getId()));

        discipline.setStudents(students);
        disciplineRepository.save(discipline);
        return DisciplineMapper.toDto(discipline);
    }

    public DisciplineResponseDto addTitularTeacherByDiscipline(String disciplineName, String emailTeacher) {
        Discipline discipline = getDisciplineByName(disciplineName);
        if(discipline.getMainTeacherEmail() != null) {
            throw new InvalidTeacherEmailException("There is already a professor associated with this discipline");
        }

        Optional<Coordinator> coordinatorOptional = coordinatorRepository.findByEmail(emailTeacher);
        Teacher teacher;
        Coordinator coordinator;
        if (coordinatorOptional.isEmpty()) {
            teacher = teacherRepository.findByEmail(emailTeacher).orElseThrow(() -> new EntityNotFoundException());
            teacher.setMainTeacher(discipline);
            teacherRepository.save(teacher);
        } else {
            coordinator = coordinatorOptional.get();
            coordinator.setMainTeacher(discipline);
            coordinatorRepository.save(coordinator);
        }


        discipline.setMainTeacherEmail(emailTeacher);
        disciplineRepository.save(discipline);
        return DisciplineMapper.toDto(discipline);
    }

    public DisciplineResponseDto removeTitularTeacherDiscipline(String disciplineName, String emailTeacher) {
        Discipline discipline = getDisciplineByName(disciplineName);
        if(discipline.getMainTeacherEmail().equalsIgnoreCase(emailTeacher)) {
            discipline.setMainTeacherEmail(null);
            disciplineRepository.save(discipline);
        } else {
            throw new InvalidTeacherEmailException("Error: Teacher not found by this email");
        }
        DisciplineResponseDto responseDto = DisciplineMapper.toDto(discipline);
        return responseDto;
    }

    public DisciplineResponseDto addSubstituteTeacherDiscipline(String disciplineName, String emailTeacher) {
        Discipline discipline = getDisciplineByName(disciplineName);
        if(discipline.getSubsTeacherEmail() != null) {
            throw new InvalidTeacherEmailException("There is already a professor associated with this discipline");
        }

        Optional<Coordinator> coordinatorOptional = coordinatorRepository.findByEmail(emailTeacher);
        Teacher teacher;
        Coordinator coordinator;
        if (coordinatorOptional.isEmpty()) {
            teacher = teacherRepository.findByEmail(emailTeacher).orElseThrow(() -> new EntityNotFoundException());
            teacher.setSubsTeacher(discipline);
            teacherRepository.save(teacher);
        } else {
            coordinator = coordinatorOptional.get();
            coordinator.setSubsTeacher(discipline);
            coordinatorRepository.save(coordinator);
        }

        discipline.setSubsTeacherEmail(emailTeacher);
        disciplineRepository.save(discipline);
        DisciplineResponseDto responseDto = DisciplineMapper.toDto(discipline);
        return responseDto;
    }

    public DisciplineResponseDto removeSubstituteTeacherDiscipline(String disciplineName, String emailTeacher) {
        Discipline discipline = getDisciplineByName(disciplineName);

        if(discipline.getSubsTeacherEmail().equalsIgnoreCase(emailTeacher)) {
            discipline.setSubsTeacherEmail(null);
            disciplineRepository.save(discipline);
        } else {
            throw new InvalidTeacherEmailException("There is already a professor associated with this discipline");
        }
        DisciplineResponseDto responseDto = DisciplineMapper.toDto(discipline);
        return responseDto;
    }
}
