package com.example.configservice.services;

import com.example.configservice.entities.Grading;
import com.example.configservice.entities.Subject;
import com.example.configservice.repositories.GradingRepository;
import com.example.configservice.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradingService {

    private final GradingRepository gradingRepository;
    private final SubjectRepository subjectRepository;

    public GradingService(GradingRepository gradingRepository,
                          SubjectRepository subjectRepository) {
        this.gradingRepository = gradingRepository;
        this.subjectRepository=subjectRepository;
    }

    // Retrieve all gradings
    public List<Grading> getAllGradings() {
        return gradingRepository.findAll();
    }

    // Retrieve a specific grading by ID
    public Optional<Grading> getGradingById(Integer id) {
        return gradingRepository.findById(id);
    }

    // Create a new grading
    public Grading createGrading(Grading grading) {
        return gradingRepository.save(grading);
    }

    // Update an existing grading
    public Grading updateGrading(Grading grading) {
        return gradingRepository.save(grading);
    }

    // Delete a grading by ID
    public void deleteGrading(Integer id) {
        gradingRepository.deleteById(id);
    }

    //add grading to subject
//    public Subject assignGradingToSubject(Integer subjectId, Integer gradingId) {
//        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
//        Optional<Grading> gradingOptional = gradingRepository.findById(gradingId);
//
//        if (subjectOptional.isEmpty()) {
//            throw new RuntimeException("Subject not found with ID: " + subjectId);
//        }
//        if (gradingOptional.isEmpty()) {
//            throw new RuntimeException("Grading not found with ID: " + gradingId);
//        }
//
//        Subject subject = subjectOptional.get();
//        subject.setGrading(gradingOptional.get());
//        return subjectRepository.save(subject);
//    }
}
