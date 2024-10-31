package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.repositories.CoordinatorRepository;
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
        return repository.save(coordinator);
    }

    public List<Coordinator> getAllCoordinators() {
        List<Coordinator> response = repository.findAll();
        if(response.isEmpty()){
            throw new EmptyListException("Error: There are no registered coordinators");
        }
        return response;
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
