package com.example.studentservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class PerformanceReportDto {
    private String studentName;
    private String admissionNumber;
    private String studentClass;
    private String stream;
    private List<TermPerformanceDto> termPerformances;
    private double averageScore;
    private String overallGrade;
}
