package com.compass.Desafio_02.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter

@Entity
@Table(name = "coordinator")
public class Coordinator extends Person{

    @OneToOne
    private Course course;


}
