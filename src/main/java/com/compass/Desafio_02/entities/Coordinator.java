package com.compass.Desafio_02.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Coordinator extends Person implements Serializable {

    @OneToOne(mappedBy = "coordinator")
    @JsonIgnore
    private Course course;

    @OneToOne
    private Discipline mainTeacher;

    @OneToOne
    private Discipline subsTeacher;
}
