package com.example.configservice.repositories;

import com.example.configservice.entities.Grading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradingRepository extends JpaRepository<Grading,Integer> {
    //Optional<Grading> findById(Integer id);

    Optional<Grading> findByGrade(String name);
}
