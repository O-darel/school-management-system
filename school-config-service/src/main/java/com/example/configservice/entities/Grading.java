package com.example.configservice.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Grading")
public class Grading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "min_score", nullable = false)
    private int minScore;

    @Column(name = "max_score", nullable = false)
    private int maxScore;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "points", nullable = false)
    private int points;
}
