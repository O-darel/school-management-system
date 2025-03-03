package com.example.studentservice.dto;

import com.example.studentservice.entities.Exam;
import com.example.studentservice.entities.Student;

public class ReportResponseDto {
    private Long id;
    private Exam exam;
    private String subject;
    private Student student;
    private Double score;
    private String grade;
    private Integer points;
}
