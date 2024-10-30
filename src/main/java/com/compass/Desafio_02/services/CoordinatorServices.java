package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.repositories.CoordinatorRepository;
import com.compass.Desafio_02.web.exception.CoordinatorInCourseUniqueViolationException;
import com.compass.Desafio_02.web.exception.CourseNotNullException;
import com.compass.Desafio_02.web.exception.EmptyListException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinatorServices {

    @Autowired
    private CoordinatorRepository repository;

    public Coordinator getCoordinatorById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Coordinator not found")
        );
    }

    public Coordinator createCoordinator(Coordinator coordinator) {
        if(coordinator.getCourse().getCoordinator() != null){
            throw new CoordinatorInCourseUniqueViolationException("Error: This coordinator already has a course in progress");
        } else if(coordinator.getCourse() == null){
            throw new CourseNotNullException("Error: The course can't be null");
        }

        return repository.save(coordinator);
    }

    public List<Coordinator> getAllCoordinators() {
        List<Coordinator> coordinators = repository.findAll();
        if(coordinators.isEmpty()){
            throw new EmptyListException("Error: There are no registered coordinators");
        }
        return coordinators;
    }

    public Coordinator updateCoordinator(Coordinator update) {
        Coordinator coordinator = getCoordinatorById(update.getId());

        coordinator.setFirstName(update.getFirstName());
        coordinator.setLastName(update.getLastName());
        coordinator.setEmail(update.getEmail());
        coordinator.setBirthDate(update.getBirthDate());
        coordinator.setPassword(update.getPassword());
        coordinator.setRole(update.getRole());
        coordinator.setCourse(update.getCourse());

        return repository.save(coordinator);
    }

    public void deleteCoordinator(long id) {
        getCoordinatorById(id);
        repository.deleteById(id);
    }
}
