package com.example.studentservice.service;

import com.example.studentservice.client.ConfigServiceClient;
import com.example.studentservice.dto.*;
import com.example.studentservice.entities.Exam;
import com.example.studentservice.entities.ExamRecord;
import com.example.studentservice.entities.Student;
import com.example.studentservice.repository.ExamRecordRepository;
import com.example.studentservice.repository.ExamRepository;
import com.example.studentservice.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamRecordService {

    private final ExamRecordRepository examRecordRepository;
    private final ExamRepository examRepository;
    private final StudentRepository studentRepository;
    private final ConfigServiceClient configServiceClient; // For grading

    public ExamRecordResponseDto recordExamResult(ExamRecordRequestDto request) {
        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        // Check if the student has already taken this exam
        boolean alreadyTaken = examRecordRepository.existsByStudentIdAndExamIdAndTerm(request.getStudentId(),
                request.getExamId(),request.getTerm());
        if (alreadyTaken) {
            throw new RuntimeException("Student has already taken this exam!");
        }

        GradeDto gradeDto = determineGrade(request.getScore());

        ExamRecord examRecord = new ExamRecord();
        examRecord.setExam(exam);
        examRecord.setStudent(student);
        examRecord.setScore(request.getScore());
        examRecord.setGrade(gradeDto.getGrade());
        examRecord.setPoints(gradeDto.getPoints());
        examRecord.setTerm(request.getTerm());
        //save
        examRecord = examRecordRepository.save(examRecord);
        //response
        ExamRecordResponseDto examResponse=new ExamRecordResponseDto();
        examResponse.setId(examRecord.getId());
        examResponse.setScore(examRecord.getScore());
        examResponse.setGrade(examRecord.getGrade());
        examResponse.setTerm(examRecord.getTerm().name());
        examResponse.setPoints(examRecord.getPoints());
        examResponse.setExam(examRecord.getExam().getName());
        examResponse.setStudentName(examRecord.getStudent().getName());
        examResponse.setAdmissionNumber(examRecord.getStudent().getAdmissionNumber());
        examResponse.setSubject(examRecord.getExam().getSubjectName());

        SchoolClassDto schoolClass;
        try {
            schoolClass = configServiceClient.getClassById(examRecord.getStudent().getClassId());
        } catch (Exception e) {
            throw new RuntimeException(" Invalid classId: " + examRecord.getStudent().getClassId());

        }

        StreamDto stream;
        try {
            stream = configServiceClient.getStreamById(examRecord.getStudent().getStreamId());
        } catch (Exception e) {
            throw new RuntimeException("Invalid streamId: " + examRecord.getStudent().getStreamId());
        }
        examResponse.setStudentClass(schoolClass.getName());
        examResponse.setStream(stream.getName());

        return examResponse;
    }

//    private String determineGrade(Double score) {
//        List<GradeRequestDto> grades = configServiceClient.getAllGrades();
//
//        return grades.stream()
//                .filter(g -> score >= g.getMinScore() && score <= g.getMaxScore())
//                .map(GradeRequestDto::getGrade)
//                .findFirst()
//                .orElse("N/A"); // Default if no grade found
//    }
    private GradeDto determineGrade(Double score) {
        return configServiceClient.getAllGrades().stream()
                .filter(g -> score >= g.getMinScore() && score <= g.getMaxScore())
                .findFirst()
                .orElse(new GradeDto()); // Return an empty GradeDto if no grade is found
    }


//    public List<ExamRecordResponseDto> getExamRecordsByStudent(Long studentId) {
//        List<ExamRecord> records = examRecordRepository.findByStudentId(studentId);
//        return records.stream()
//                .map(record -> new ExamRecordResponseDto(record.getId(), record.getExam(),
//                        record.getStudent().getId(), record.getScore(), record.getGrade(),record.getPoints()))
//                .collect(Collectors.toList());
//    }

    public List<ExamRecordResponseDto> getExamRecordsByStudent(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        SchoolClassDto schoolClass;
        try {
            schoolClass = configServiceClient.getClassById(student.getClassId());
        } catch (Exception e) {
            throw new RuntimeException(" Invalid classId: " + student.getClassId());

        }

        StreamDto stream;
        try {
            stream = configServiceClient.getStreamById(student.getStreamId());
        } catch (Exception e) {
            throw new RuntimeException("Invalid streamId: " + student.getStreamId());
        }


        List<ExamRecord> records = examRecordRepository.findByStudentId(studentId);
        return records.stream()
                .map(record -> {
                    ExamRecordResponseDto responseDto = new ExamRecordResponseDto();
                    responseDto.setId(record.getId());
                    responseDto.setExam(record.getExam().getName());
                    responseDto.setStudentName(record.getStudent().getName());
                    responseDto.setAdmissionNumber(record.getStudent().getAdmissionNumber());
                    responseDto.setScore(record.getScore());
                    responseDto.setGrade(record.getGrade());
                    responseDto.setPoints(record.getPoints());
                    responseDto.setTerm(record.getTerm().name());
                    responseDto.setSubject(record.getExam().getSubjectName());
                    responseDto.setStudentClass(schoolClass.getName());
                    responseDto.setStream(stream.getName());
                    return responseDto;
                })
                .collect(Collectors.toList());
    }



    // ✅ READ - Get all exam records
    public List<ExamRecordResponseDto> getAllExamRecords() {
        return examRecordRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // ✅ READ - Get exam record by ID
    public ExamRecordResponseDto getExamRecordById(Long id) {
        ExamRecord examRecord = examRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam record not found"));
        return mapToResponseDto(examRecord);
    }

    // ✅ READ - Get exam records by student ID
//    public List<ExamRecordResponseDto> getExamRecordsByStudent(Long studentId) {
//        return examRecordRepository.findByStudentId(studentId).stream()
//                .map(this::mapToResponseDto)
//                .collect(Collectors.toList());
//    }

    // ✅ UPDATE Exam Record
    public ExamRecordResponseDto updateExamRecord(Long id, ExamRecordRequestDto request) {
        ExamRecord examRecord = examRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam record not found"));

        examRecord.setScore(request.getScore());
        GradeDto gradeDto = determineGrade(request.getScore());
        examRecord.setGrade(gradeDto.getGrade());
        examRecord.setPoints(gradeDto.getPoints());
        examRecord.setTerm(request.getTerm());

        examRecord = examRecordRepository.save(examRecord);
        return mapToResponseDto(examRecord);
    }

    // ✅ DELETE Exam Record
    public void deleteExamRecord(Long id) {
        if (!examRecordRepository.existsById(id)) {
            throw new RuntimeException("Exam record not found");
        }
        examRecordRepository.deleteById(id);
    }



    // 📌 Helper method to map ExamRecord entity to DTO
    private ExamRecordResponseDto mapToResponseDto(ExamRecord examRecord) {
        ExamRecordResponseDto responseDto = new ExamRecordResponseDto();
        responseDto.setId(examRecord.getId());
        responseDto.setExam(examRecord.getExam().getName());
        responseDto.setStudentName(examRecord.getStudent().getName());
        responseDto.setAdmissionNumber(examRecord.getStudent().getAdmissionNumber());
        responseDto.setScore(examRecord.getScore());
        responseDto.setGrade(examRecord.getGrade());
        responseDto.setPoints(examRecord.getPoints());
        responseDto.setTerm(examRecord.getTerm().name());
        responseDto.setSubject(examRecord.getExam().getSubjectName());

        try {
            SchoolClassDto schoolClass = configServiceClient.getClassById(examRecord.getStudent().getClassId());
            responseDto.setStudentClass(schoolClass.getName());
        } catch (Exception e) {
            responseDto.setStudentClass("Unknown Class");
        }

        try {
            StreamDto stream = configServiceClient.getStreamById(examRecord.getStudent().getStreamId());
            responseDto.setStream(stream.getName());
        } catch (Exception e) {
            responseDto.setStream("Unknown Stream");
        }

        return responseDto;
    }



}
