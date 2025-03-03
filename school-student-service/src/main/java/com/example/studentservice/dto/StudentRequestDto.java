package com.example.studentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDto {
    private String name;
    private String dateOfBirth;
    private String address;
    private String contactInfo;
    private String enrollmentDate;
    private Long classId;  // Provided by the user
    private Long streamId; // Provided by the user
}

