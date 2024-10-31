package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Registration;
import com.compass.Desafio_02.repositories.RegistrationRepository;
import com.compass.Desafio_02.web.exception.EmptyListException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RegistrationServices {

    @Autowired
    private RegistrationRepository repository;

    public Registration getRegistrationById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Registration not found")
        );
    }

    public Registration createRegistration(Registration registration) {
            return repository.save(registration);
    }

    public List<Registration> getAllRegistrations() {
        List<Registration> registrations = repository.findAll();
        if(registrations.isEmpty()){
            throw new EmptyListException("Error: There are no registered registrations");
        }
        return registrations;
    }

    public Registration updateRegistration(Registration update) {
        Registration registration = getRegistrationById(update.getId());

        registration.setCourse(update.getCourse());
        registration.setStudent(update.getStudent());

        return repository.save(registration);
    }

    public void deleteRegistration(long id) {
        getRegistrationById(id);
        repository.deleteById(id);
    }
}
