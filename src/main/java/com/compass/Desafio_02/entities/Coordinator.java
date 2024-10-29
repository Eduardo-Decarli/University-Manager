package com.compass.Desafio_02.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter

@Entity
@Table(name = "coordinator")
public class Coordinator extends Person{

    //To-Do -> Organizar a relação entre coordinator e Teacher com Discipline
    //private List<Discipline> disciplines;

    @OneToOne(mappedBy = "coordinator", cascade = CascadeType.ALL)
    private Course course;
}
