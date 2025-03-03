package com.example.studentservice.service;

import com.example.studentservice.client.ConfigServiceClient;
import com.example.studentservice.dto.*;
import com.example.studentservice.entities.ExamRecord;
import com.example.studentservice.entities.Student;
import com.example.studentservice.repository.ExamRecordRepository;
import com.example.studentservice.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ExamRecordRepository examRecordRepository;
    private final StudentRepository studentRepository;
    private final ConfigServiceClient configServiceClient;

    public PerformanceReportDto getStudentReport(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<ExamRecord> records = examRecordRepository.findByStudentId(studentId);
        if (records.isEmpty()) {
            throw new RuntimeException("No exam records found for student.");
        }

        // Group records by term
        Map<String, List<ExamRecord>> recordsByTerm = records.stream()
                .collect(Collectors.groupingBy(record -> record.getTerm().name()));

        List<TermPerformanceDto> termPerformances = new ArrayList<>();
        double totalScore = 0;
        int totalSubjects = 0;

        for (Map.Entry<String, List<ExamRecord>> entry : recordsByTerm.entrySet()) {
            String term = entry.getKey();
            List<ExamRecord> termRecords = entry.getValue();

            double termTotalScore = termRecords.stream().mapToDouble(ExamRecord::getScore).sum();
            int termSubjects = termRecords.size();
            double termAverage = termSubjects == 0 ? 0 : termTotalScore / termSubjects;

            GradeDto gradeDto = determineGrade(termAverage);

            List<SubjectPerformanceDto> subjects = termRecords.stream()
                    .map(record -> {
                        SubjectPerformanceDto subjectPerformance = new SubjectPerformanceDto();
                        subjectPerformance.setSubject(record.getExam().getSubjectName());
                        subjectPerformance.setScore(record.getScore());
                        subjectPerformance.setGrade(record.getGrade());
                        return subjectPerformance;
                    }).collect(Collectors.toList());

            TermPerformanceDto termPerformance = new TermPerformanceDto();
            termPerformance.setTerm(term);
            termPerformance.setAverageScore(termAverage);
            termPerformance.setGrade(gradeDto.getGrade());
            termPerformance.setSubjects(subjects);

            termPerformances.add(termPerformance);
            totalScore += termTotalScore;
            totalSubjects += termSubjects;
        }

        double averageScore = totalSubjects == 0 ? 0 : totalScore / totalSubjects;
        GradeDto overallGrade = determineGrade(averageScore);

        PerformanceReportDto report = new PerformanceReportDto();
        report.setStudentName(student.getName());
        report.setAdmissionNumber(student.getAdmissionNumber());

        // Fetch Class and Stream
        SchoolClassDto schoolClass = configServiceClient.getClassById(student.getClassId());
        StreamDto stream = configServiceClient.getStreamById(student.getStreamId());

        report.setStudentClass(schoolClass.getName());
        report.setStream(stream.getName());
        report.setTermPerformances(termPerformances);
        report.setAverageScore(averageScore);
        report.setOverallGrade(overallGrade.getGrade());

        return report;
    }

    private GradeDto determineGrade(Double score) {
        return configServiceClient.getAllGrades().stream()
                .filter(g -> score >= g.getMinScore() && score <= g.getMaxScore())
                .findFirst()
                .orElse(new GradeDto()); // Default if no grade is found
    }
}

