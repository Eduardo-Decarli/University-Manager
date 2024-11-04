package com.compass.Desafio_02.jwt;


import com.compass.Desafio_02.entities.Coordinator;
import com.compass.Desafio_02.entities.Person;
import com.compass.Desafio_02.entities.Student;
import com.compass.Desafio_02.entities.Teacher;
import com.compass.Desafio_02.entities.enumeration.Role;
import com.compass.Desafio_02.repositories.CoordinatorRepository;
import com.compass.Desafio_02.repositories.StudentRepository;
import com.compass.Desafio_02.repositories.TeacherRepository;
import com.compass.Desafio_02.services.CoordinatorServices;
import com.compass.Desafio_02.services.StudentService;
import com.compass.Desafio_02.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final CoordinatorRepository coordinatorRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Coordinator> coordinator = coordinatorRepository.findByEmail(email);
        Optional<Teacher> teacher = teacherRepository.findByEmail(email);
        Optional<Student> student = studentRepository.findByEmail(email);

        if (coordinator.isPresent()) {
            Coordinator c = coordinator.get();
            return new JwtUserDetails(c);
        } else if (teacher.isPresent()) {
            Teacher t = teacher.get();
            return new JwtUserDetails(t);
        } else if (student.isPresent()) {
            Student s = student.get();
            return new JwtUserDetails(s);
        }
        throw new RuntimeException("User not found.");
    }

    public JwtToken getTokenAuthenticated(String email) {
        Optional<Coordinator> coordinator = coordinatorRepository.findByEmail(email);
        Optional<Teacher> teacher = teacherRepository.findByEmail(email);
        Optional<Student> student = studentRepository.findByEmail(email);
        Role role = null;

        if (coordinator.isPresent()) {
            Coordinator c = coordinator.get();
            role = c.getRole();
        } else if (teacher.isPresent()) {
            Teacher t = teacher.get();
            role = t.getRole();
        } else if (student.isPresent()) {
            Student s = student.get();
            role = s.getRole();
        }

        return JwtUtils.createToken(email, role.name().substring("ROLE_".length()));
    }

}