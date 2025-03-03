package com.example.studentservice.dto;


import lombok.Data;

@Data
public class SubjectPerformanceDto {
    private String subject;
    private double score;
    private String grade;
}

