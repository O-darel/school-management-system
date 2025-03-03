package com.example.configservice.services;

import com.example.configservice.entities.Subject;
import com.example.configservice.repositories.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> getSubjectById(Integer id) {
        return subjectRepository.findById(id);
    }

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject updateSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void deleteSubject(Integer id) {
        subjectRepository.deleteById(id);
    }
}
