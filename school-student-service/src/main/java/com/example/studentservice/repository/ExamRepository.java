package com.example.studentservice.repository;

import com.example.studentservice.entities.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
