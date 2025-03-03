package com.example.studentservice.dto;

import com.example.studentservice.entities.Exam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamRecordResponseDto {
    private Long id;
    //private Long examId;
    private String studentName;//
    private String admissionNumber;//
    private String exam;//
    private String subject;//
    private Double score;//
    private String grade;//
    private Integer points;//
    private String studentClass;
    private String stream;
    private String term; //

}
