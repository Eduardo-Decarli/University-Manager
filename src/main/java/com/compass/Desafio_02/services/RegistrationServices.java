package com.compass.Desafio_02.services;

import com.compass.Desafio_02.entities.Course;
import com.compass.Desafio_02.entities.Registration;
import com.compass.Desafio_02.repositories.RegistrationRepository;
import com.compass.Desafio_02.web.dto.RegistrationCreateDto;
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

    public RegistrationResponseDto getRegistrationById(Long id) {
        Registration registration = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Registration not found")
        );
        return RegistrationMapper.toDto(registration);
    }

    public RegistrationResponseDto createRegistration(RegistrationCreateDto registrationDto) {
        Registration registration = RegistrationMapper.toRegistration(registrationDto);
        RegistrationResponseDto response = RegistrationMapper.toDto(repository.save(registration));
        return response;
    }

    public List<RegistrationResponseDto> getAllRegistrations() {
        List<Registration> response = repository.findAll();
        if(response.isEmpty()){
            throw new EmptyListException("Error: There are no registered registrations");
        }
        return RegistrationMapper.toListDto(response);
    }

    public RegistrationResponseDto updateRegistration(Long id, Registration update) {
       Registration registration = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Error: Registration not found")
        );;

        registration.setCourse(update.getCourse());
        registration.setStudent(update.getStudent());

        repository.save(registration);
        return RegistrationMapper.toDto(registration);
    }

    public void deleteRegistration(long id) {
        getRegistrationById(id);
        repository.deleteById(id);
    }
}
