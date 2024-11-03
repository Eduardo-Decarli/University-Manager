package com.compass.Desafio_02.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student extends Person implements Serializable {

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JsonIgnore
    private Course course;

    @OneToOne(mappedBy = "student")
    @JsonIgnore
    private Registration registration;
}
