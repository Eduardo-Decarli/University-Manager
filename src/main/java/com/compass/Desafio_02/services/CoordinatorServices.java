package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.entities.Discipline;
import com.compass.Desafio_02.repositories.CoordinatorRepository;
import com.compass.Desafio_02.web.dto.CoordinatorCreateDto;
import com.compass.Desafio_02.web.dto.CoordinatorResponseDto;
import com.compass.Desafio_02.web.dto.CourseResponseDto;
import com.compass.Desafio_02.web.dto.DisciplineResponseDto;
import com.compass.Desafio_02.web.dto.mapper.CoordinatorMapper;
import com.compass.Desafio_02.web.dto.mapper.CourseMapper;
import com.compass.Desafio_02.web.dto.mapper.DisciplineMapper;
import com.compass.Desafio_02.web.exception.CourseNotNullException;
import com.compass.Desafio_02.web.exception.EmptyListException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoordinatorServices {

    @Autowired
    private CoordinatorRepository repository;

    public CoordinatorResponseDto getCoordinatorById(Long id) {
        Coordinator response = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Coordinator not found")
        );
        return CoordinatorMapper.toDto(response);
    }

    public CoordinatorResponseDto createCoordinator(CoordinatorCreateDto coordinator) {
        Coordinator response = CoordinatorMapper.toCoordinator(coordinator);
        Coordinator coordinatorSaved = repository.save(response);
        return CoordinatorMapper.toDto(coordinatorSaved);
    }

    public List<CoordinatorResponseDto> getAllCoordinators() {
        List<Coordinator> response = repository.findAll();
        if(response.isEmpty()){
            throw new EmptyListException("Error: There are no registered coordinators");
        }
        return CoordinatorMapper.toListDto(response);
    }

    public CoordinatorResponseDto updateCoordinator(Long id, CoordinatorCreateDto update) {
        Coordinator coordinator = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Coordinator not found")
        );

        coordinator.setFirstName(update.getFirstName());
        coordinator.setLastName(update.getLastName());
        coordinator.setEmail(update.getEmail());
        coordinator.setBirthDate(update.getBirthDate());
        coordinator.setPassword(update.getPassword());
        coordinator.setRole(update.getRole());

        repository.save(coordinator);
        return CoordinatorMapper.toDto(coordinator);
    }

    public void deleteCoordinator(long id) {
        getCoordinatorById(id);
        repository.deleteById(id);
    }

    public CoordinatorResponseDto getMyData(Long id) {
        Coordinator coordinator = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Coordinator not found")
        );
        return CoordinatorMapper.toDto(coordinator);
    }


    public CoordinatorResponseDto updateMyData(Long id, CoordinatorCreateDto update) {
        Coordinator coordinatorSaved = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Coordinator not found")
        );

        coordinatorSaved.setFirstName(update.getFirstName());
        coordinatorSaved.setLastName(update.getLastName());
        coordinatorSaved.setEmail(update.getEmail());
        coordinatorSaved.setBirthDate(update.getBirthDate());
        coordinatorSaved.setPassword(update.getPassword());
        coordinatorSaved.setRole(update.getRole());

        repository.save(coordinatorSaved);
        return CoordinatorMapper.toDto(coordinatorSaved);
    }

    public CourseResponseDto getMyCourse(Long id) {
        Coordinator coordinator = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Coordinator not found")
        );
        CourseResponseDto course = CourseMapper.toDto(coordinator.getCourse());
        return course;
    }

    public List<DisciplineResponseDto> getDisciplinesInCourse(Long id) {
        Coordinator coordinator = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Coordinator not found")
        );
        if(coordinator.getCourse() == null) {
            throw new CourseNotNullException("Error: No courses associated with this coordinator");
        }
        List<Discipline> disciplines = coordinator.getCourse().getDisciplines();
        if(disciplines.isEmpty()) {
            throw new EmptyListException("Error: The course does not have any discipline");
        }
        return DisciplineMapper.toListDto(disciplines);
    }

    public List<DisciplineResponseDto> getMyDisciplines(Long id) {
        Coordinator coordinator = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Coordinator not found")
        );
        List<Discipline> disciplines = new ArrayList<>();
        if(coordinator.getMainTeacher() != null){
          disciplines.add(coordinator.getMainTeacher());
        }
        if(coordinator.getSubsTeacher() != null){
            disciplines.add(coordinator.getSubsTeacher());
        }
        return DisciplineMapper.toListDto(disciplines);
    }
}
