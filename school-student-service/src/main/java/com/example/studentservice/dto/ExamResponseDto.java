package com.example.studentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResponseDto {
    private Long id;
    private String name;
    private Long subjectId;
    private String subject;
    private Date examDate;

}