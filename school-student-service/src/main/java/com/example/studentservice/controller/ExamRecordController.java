package com.example.studentservice.controller;

import com.example.studentservice.dto.ExamRecordRequestDto;
import com.example.studentservice.dto.ExamRecordResponseDto;
import com.example.studentservice.service.ExamRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students/exam-records")
@RequiredArgsConstructor
public class ExamRecordController {

    private final ExamRecordService examRecordService;

    @PostMapping
    public ResponseEntity<ExamRecordResponseDto> recordExam(@RequestBody ExamRecordRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examRecordService.recordExamResult(request));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ExamRecordResponseDto>> getStudentExamRecords(@PathVariable Long studentId) {
        return ResponseEntity.ok(examRecordService.getExamRecordsByStudent(studentId));
    }


//    // ✅ CREATE Exam Record
//    @PostMapping
//    public ResponseEntity<ExamRecordResponseDto> recordExam(@RequestBody ExamRecordRequestDto request) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(examRecordService.recordExamResult(request));
//    }

    // ✅ READ - Get all exam records
    @GetMapping
    public ResponseEntity<List<ExamRecordResponseDto>> getAllExamRecords() {
        return ResponseEntity.ok(examRecordService.getAllExamRecords());
    }

//    // ✅ READ - Get exam records by student ID
//    @GetMapping("/student/{studentId}")
//    public ResponseEntity<List<ExamRecordResponseDto>> getStudentExamRecords(@PathVariable Long studentId) {
//        return ResponseEntity.ok(examRecordService.getExamRecordsByStudent(studentId));
//    }

    // ✅ READ - Get exam record by ID
    @GetMapping("/{id}")
    public ResponseEntity<ExamRecordResponseDto> getExamRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(examRecordService.getExamRecordById(id));
    }

    // ✅ UPDATE Exam Record
    @PutMapping("/{id}")
    public ResponseEntity<ExamRecordResponseDto> updateExamRecord(@PathVariable Long id, @RequestBody ExamRecordRequestDto request) {
        return ResponseEntity.ok(examRecordService.updateExamRecord(id, request));
    }

    // ✅ DELETE Exam Record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamRecord(@PathVariable Long id) {
        examRecordService.deleteExamRecord(id);
        return ResponseEntity.noContent().build();
    }
}
