package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Discipline;
import com.compass.Desafio_02.repositories.DisciplineRepository;
import com.compass.Desafio_02.web.exception.EmptyListException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DisciplineServices {


    @Autowired
    private DisciplineRepository repository;

    public Discipline getDisciplineById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Discipline not found")
        );
    }

    public Discipline createDiscipline(Discipline discipline) {
        return repository.save(discipline);
    }

    public List<Discipline> getAllDisciplines() {
        List<Discipline> disciplines = repository.findAll();
        if(disciplines.isEmpty()){
            throw new EmptyListException("Error: There are no registered disciplines");
        }
        return disciplines;
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
}
