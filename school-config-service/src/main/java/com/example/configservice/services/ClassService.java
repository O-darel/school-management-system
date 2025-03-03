package com.example.configservice.services;


import com.example.configservice.dto.ClassCreationDto;
import com.example.configservice.entities.Class;
import com.example.configservice.entities.Grading;
import com.example.configservice.entities.Stream;
import com.example.configservice.entities.Subject;
import com.example.configservice.repositories.ClassRepository;
import com.example.configservice.repositories.GradingRepository;
import com.example.configservice.repositories.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final GradingRepository gradingRepository;

    public ClassService(ClassRepository classRepository,
                        SubjectRepository subjectRepository,
                        GradingRepository gradingRepository){
        this.classRepository=classRepository;
        this.subjectRepository=subjectRepository;
        this.gradingRepository = gradingRepository;
    }

    //create class
    public Class createClass(ClassCreationDto classCreationDto){
       //Optional <Class> thisClass=classRepository.findByName(classCreationDto.getName());
        Class newClass=new Class();
        newClass.setName(classCreationDto.getName());
        return classRepository.save(newClass);

    }

    //get classes
    public List<Class> getClasses(){
        return classRepository.findAll();
    }

    // Retrieve a specific class by ID
    public Optional<Class> getClassById(Integer id) {
        return classRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Set<Stream> getStreamsForClass(Integer classId) {
        Optional<Class> clazz = classRepository.findById(classId);
        return clazz.map(Class::getStreams).orElse(null);
    }

    // Update an existing class
    public Class updateClass(Class clazz) {
        return classRepository.save(clazz);
    }

    // Delete a class by its ID
    public void deleteClass(Integer id) {
        classRepository.deleteById(id);
    }

    //subject related

    // Retrieve the subjects associated with a given class
    @Transactional(readOnly = true)
    public Set<Subject> getSubjectsForClass(Integer classId) {
        Optional<Class> clazz = classRepository.findById(classId);
        return clazz.map(Class::getSubjects).orElse(null);
    }

    // Add a subject to a class
    @Transactional
    public Class addSubjectToClass(Integer classId, Integer subjectId) {
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        clazz.getSubjects().add(subject);
        return classRepository.save(clazz);
    }

    // Remove a subject from a class
    @Transactional
    public Class removeSubjectFromClass(Integer classId, Integer subjectId) {
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        clazz.getSubjects().remove(subject);
        return classRepository.save(clazz);
    }


//    @Transactional
//    public Class updateGradingForSubject(Integer classId, Integer subjectId, Integer gradingId) {
//        // Retrieve the class
//        Class clazz = classRepository.findById(classId)
//                .orElseThrow(() -> new RuntimeException("Class not found"));
//
//        // Ensure the subject is associated with this class
//        Optional<Subject> optionalSubject = clazz.getSubjects().stream()
//                .filter(subject -> subject.getId().equals(subjectId))
//                .findFirst();
//
//        if (!optionalSubject.isPresent()) {
//            throw new RuntimeException("Subject not found in this class");
//        }
//        Subject subject = optionalSubject.get();
//
//        // Retrieve the new grading details
//        Grading grading = gradingRepository.findById(gradingId)
//                .orElseThrow(() -> new RuntimeException("Grading not found"));
//
//        // Update the subject's grading
//        subject.setGrading(grading);
//        subjectRepository.save(subject); // Persist the update
//
//        return clazz;
//    }

}
