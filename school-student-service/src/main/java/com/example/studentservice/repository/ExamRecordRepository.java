package com.example.studentservice.repository;

import com.example.studentservice.entities.ExamRecord;
import com.example.studentservice.entities.TermEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRecordRepository extends JpaRepository<ExamRecord, Long> {
    List<ExamRecord> findByStudentId(Long studentId);
    List<ExamRecord> findByExamId(Long examId);
    List<ExamRecord> findByTerm(TermEnum term);
    boolean existsByStudentIdAndExamId(Long studentId, Long examId);
    boolean existsByStudentIdAndExamIdAndTerm(Long studentId, Long examId,TermEnum term);
}
