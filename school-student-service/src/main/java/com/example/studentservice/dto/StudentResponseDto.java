package com.example.studentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {
    private Long id;
    private String name;
    private String admissionNumber;
    private String dateOfBirth;
    private String address;
    private String contactInfo;
    private String enrollmentDate;
    private SchoolClassDto schoolClass;
    private StreamDto stream;
}

