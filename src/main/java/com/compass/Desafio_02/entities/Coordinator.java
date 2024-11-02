package com.compass.Desafio_02.entities;

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

    @OneToOne(cascade = CascadeType.ALL)
    private Course course;

    @OneToOne
    private Discipline mainTeacher;

    @OneToOne
    private Discipline subsTeacher;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Coordinator that = (Coordinator) o;
        return Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), course);
    }
}
