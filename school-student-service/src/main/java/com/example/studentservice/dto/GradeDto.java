package com.example.studentservice.dto;

import lombok.Data;

@Data
public class GradeDto {
    private Integer id;
    private Integer minScore;
    private Integer maxScore;
    private String grade;
    private Integer points;
}
