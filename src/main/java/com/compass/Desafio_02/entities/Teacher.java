package com.compass.Desafio_02.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Teacher extends Person {

    @OneToMany(mappedBy = "fullProfessor", cascade = CascadeType.ALL)
    private List<Discipline> disciplines;

    @OneToMany
    @JoinColumn(name = "teacher_id")
    private List<Course> courses;
}
