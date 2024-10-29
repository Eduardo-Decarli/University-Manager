package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.repositories.CoordinatorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CoordinatorServices {

    @Autowired
    private CoordinatorRepository repository;

    public List<Coordinator> getAllCoordinators() {
        return repository.findAll();
    }

    public Coordinator getCoordinatorById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(
                () -> new Exception("Coordinator not found")
        );
    }

    public Coordinator createCoordinator(Coordinator coordinator) throws Exception {
        if(coordinator.getCourse().getCoordinator() != null){
            throw new Exception("Error: This coordinator already has a course in progress");
        } else if(coordinator.getCourse() == null){
            throw new Exception("Error: The course can't be null");
        }

        return repository.save(coordinator);
    }

    public Coordinator updateCoordinator(long id, Coordinator newCoordinator) throws Exception {
        Coordinator coordinator = getCoordinatorById(id);

        coordinator.setFirstName(newCoordinator.getFirstName());
        coordinator.setLastName(newCoordinator.getLastName());
        coordinator.setEmail(newCoordinator.getEmail());
        coordinator.setDateOfBirth(newCoordinator.getDateOfBirth());
        coordinator.setPassword(newCoordinator.getPassword());
        coordinator.setRole(newCoordinator.getRole());
        coordinator.setCourse(newCoordinator.getCourse());

        return repository.save(coordinator);
    }

    public void deleteCoordinator(long id) throws Exception {
        repository.deleteById(id);
    }
}
