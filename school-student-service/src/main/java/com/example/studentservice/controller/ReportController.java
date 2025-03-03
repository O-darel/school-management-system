package com.example.studentservice.controller;

import com.example.studentservice.dto.PerformanceReportDto;
import com.example.studentservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<PerformanceReportDto> getStudentReport(@PathVariable Long studentId) {
        return ResponseEntity.ok(reportService.getStudentReport(studentId));
    }
}
