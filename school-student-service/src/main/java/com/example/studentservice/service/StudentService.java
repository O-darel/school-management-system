package com.example.studentservice.service;

import com.example.studentservice.client.ConfigServiceClient;
import com.example.studentservice.dto.SchoolClassDto;
import com.example.studentservice.dto.StreamDto;
import com.example.studentservice.dto.StudentRequestDto;
import com.example.studentservice.dto.StudentResponseDto;
import com.example.studentservice.entities.Student;
import com.example.studentservice.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ConfigServiceClient configServiceClient;

//    private String getAuthToken() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserDetails) {
//            return "Bearer " + ((UserDetails) principal).getUsername();  // Replace with actual token retrieval
//        }
//        throw new RuntimeException("User is not authenticated");
//    }

    public StudentResponseDto createStudent(StudentRequestDto request) {
        //String authToken = getAuthToken();
        // Validate class ID
        SchoolClassDto schoolClass;
        try {
            schoolClass = configServiceClient.getClassById(request.getClassId());
        } catch (Exception e) {
            throw new RuntimeException(" Invalid classId: " + request.getClassId());

        }

        // Validate stream ID
        StreamDto stream;
        try {
            stream = configServiceClient.getStreamById(request.getStreamId());
        } catch (Exception e) {
            throw new RuntimeException("Invalid streamId: " + request.getStreamId());
        }

        // Generate admission number
        String admissionNumber = "ADM-" + System.currentTimeMillis();

        // Save student
        Student student = new Student();
        student.setName(request.getName());
        student.setAdmissionNumber(admissionNumber);
        student.setDateOfBirth(request.getDateOfBirth());
        student.setAddress(request.getAddress());
        student.setContactInfo(request.getContactInfo());
        student.setEnrollmentDate(request.getEnrollmentDate());
        student.setClassId(request.getClassId());
        student.setStreamId(request.getStreamId());

        student = studentRepository.save(student);

        // Return response with class & stream details
        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getAdmissionNumber(),
                student.getDateOfBirth(),
                student.getAddress(),
                student.getContactInfo(),
                student.getEnrollmentDate(),
                schoolClass,
                stream
        );
    }

    public StudentResponseDto getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Fetch class and stream details from Config Service
        SchoolClassDto schoolClass = configServiceClient.getClassById(student.getClassId());
        StreamDto stream = configServiceClient.getStreamById(student.getStreamId());

        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getAdmissionNumber(),
                student.getDateOfBirth(),
                student.getAddress(),
                student.getContactInfo(),
                student.getEnrollmentDate(),
                schoolClass,
                stream
        );
    }
}
