package com.example.studentservice.service;


import com.example.studentservice.client.ConfigServiceClient;
import com.example.studentservice.dto.ExamRequestDto;
import com.example.studentservice.dto.ExamResponseDto;
import com.example.studentservice.dto.SchoolClassDto;
import com.example.studentservice.dto.SubjectDto;
import com.example.studentservice.entities.Exam;
import com.example.studentservice.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final ConfigServiceClient configServiceClient;

    public ExamResponseDto createExam(ExamRequestDto request) {
        //verify subject id
        SubjectDto subjectDto;
        try {
            subjectDto = configServiceClient.getSubjectById(request.getSubjectId());
        } catch (Exception e) {
            System.err.println("Failed to fetch subject: " + e.getMessage());
            throw new RuntimeException(" Invalid classId: "+e + request.getSubjectId());

        }

        Exam exam = new Exam();
        exam.setName(request.getName());
        exam.setSubjectId(subjectDto.getId());
        exam.setExamDate(request.getExamDate());
        exam.setSubjectName(subjectDto.getName());

        exam = examRepository.save(exam);
        return new ExamResponseDto(exam.getId(), exam.getName(),
                exam.getSubjectId(),exam.getSubjectName(), exam.getExamDate());
    }

    public List<ExamResponseDto> getAllExams() {
        return examRepository.findAll().stream()
                .map(exam -> new ExamResponseDto(exam.getId(),
                        exam.getName(), exam.getSubjectId(),exam.getSubjectName(),
                        exam.getExamDate()))
                .collect(Collectors.toList());
    }

    public ExamResponseDto getExamById(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        return new ExamResponseDto(exam.getId(), exam.getName(),
                exam.getSubjectId(),exam.getSubjectName(), exam.getExamDate());
    }

    public ExamResponseDto updateExam(Long id, ExamRequestDto request) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        // Verify subject ID
        SubjectDto subjectDto;
        try {
            subjectDto = configServiceClient.getSubjectById(request.getSubjectId());
        } catch (Exception e) {
            throw new RuntimeException("Invalid subjectId: " + request.getSubjectId(), e);
        }

        exam.setName(request.getName());
        exam.setSubjectId(subjectDto.getId());
        exam.setExamDate(request.getExamDate());
        exam.setSubjectName(subjectDto.getName());

        exam = examRepository.save(exam);
        return new ExamResponseDto(exam.getId(), exam.getName(),
                exam.getSubjectId(), exam.getSubjectName(),exam.getExamDate());
    }

    public void deleteExam(Long id) {
        if (!examRepository.existsById(id)) {
            throw new RuntimeException("Exam not found");
        }
        examRepository.deleteById(id);
    }

}
