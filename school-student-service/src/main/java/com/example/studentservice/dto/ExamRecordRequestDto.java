package com.example.studentservice.dto;

import com.example.studentservice.entities.TermEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamRecordRequestDto {
    private Long examId;
    private Long studentId;
    private Double score;
    private TermEnum term;
}