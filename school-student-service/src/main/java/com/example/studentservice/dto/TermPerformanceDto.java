package com.example.studentservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class TermPerformanceDto {
    private String term;
    private double averageScore;
    private String grade;
    private List<SubjectPerformanceDto> subjects;
}
