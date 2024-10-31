package com.compass.Desafio_02.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
public class Teacher extends Person implements Serializable {

    @OneToOne
    private Course course;

    @OneToOne
    private Discipline mainTeacher;

    @OneToOne
    private Discipline subsTeacher;

    @OneToOne
    private Discipline subsTeacherOffCourse;
}
