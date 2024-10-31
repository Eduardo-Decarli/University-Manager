package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Registration;
import com.compass.Desafio_02.repositories.RegistrationRepository;
import com.compass.Desafio_02.web.dto.RegistrationResponseDto;
import com.compass.Desafio_02.web.dto.mapper.RegistrationMapper;
import com.compass.Desafio_02.web.exception.EmptyListException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationServices {

    private RegistrationRepository repository;

    public RegistrationServices(RegistrationRepository repository) {
        this.repository = repository;
    }

    public Registration getRegistrationById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Registration not found")
        );
    }

    public Registration createRegistration(Registration registration) {
            return repository.save(registration);
    }

    public List<Registration> getAllRegistrations() {
        List<Registration> response = repository.findAll();
        if(response.isEmpty()){
            throw new EmptyListException("Error: There are no registered registrations");
        }
        return response;
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

    public List<RegistrationResponseDto> getRegistrationsByCourse(Course course) {
        List<Registration> responses = repository.findByCourse(course);
        return RegistrationMapper.toListDto(responses);
    }
}
