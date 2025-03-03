package com.example.studentservice.controller;

import com.example.studentservice.dto.ExamRequestDto;
import com.example.studentservice.dto.ExamResponseDto;
import com.example.studentservice.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    public ResponseEntity<ExamResponseDto> createExam(@RequestBody ExamRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.createExam(request));
    }

    @GetMapping
    public ResponseEntity<List<ExamResponseDto>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamResponseDto> getExamById(@PathVariable Long id) {
        return ResponseEntity.ok(examService.getExamById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamResponseDto> updateExam(@PathVariable Long id, @RequestBody ExamRequestDto request) {
        return ResponseEntity.ok(examService.updateExam(id, request));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
//        examService.deleteExam(id);
//        return ResponseEntity.noContent().build();
//    }
}
