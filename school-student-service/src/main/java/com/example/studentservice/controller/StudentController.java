package com.example.studentservice.controller;

import com.example.studentservice.dto.StudentRequestDto;
import com.example.studentservice.dto.StudentResponseDto;
import com.example.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(@RequestBody StudentRequestDto request) {
        StudentResponseDto student = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudent(@PathVariable Long id) {
        StudentResponseDto student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
}

