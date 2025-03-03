package com.example.configservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

//    @ManyToOne()
//    @JoinColumn(name = "grade_id", referencedColumnName = "id")
//    private Grading grading;

}

