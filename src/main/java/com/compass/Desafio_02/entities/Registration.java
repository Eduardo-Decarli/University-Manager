package com.compass.Desafio_02.entities;

import jakarta.persistence.*;
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
public class Registration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false, name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(nullable = false, name = "course_id")
    private Course course;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
