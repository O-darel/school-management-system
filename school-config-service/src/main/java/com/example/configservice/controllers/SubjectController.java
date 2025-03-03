package com.example.configservice.controllers;

import com.example.configservice.entities.Subject;
import com.example.configservice.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/config/subjects")
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','TEACHER')")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public Optional<Subject> getSubjectById(@PathVariable Integer id) {
        return subjectService.getSubjectById(id);
    }

    @PostMapping
    public Subject createSubject(@RequestBody Subject subject) {
        return subjectService.createSubject(subject);
    }

    @PutMapping("/{id}")
    public Subject updateSubject(@PathVariable Integer id, @RequestBody Subject subject) {
        subject.setId(id);
        return subjectService.updateSubject(subject);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable Integer id) {
        subjectService.deleteSubject(id);
    }
}
