package com.example.configservice.controllers;

import com.example.configservice.dto.ClassCreationDto;
import com.example.configservice.dto.ClassCreationResponseDto;
import com.example.configservice.dto.ClassSubjectGradeDto;
import com.example.configservice.entities.Class;
import com.example.configservice.entities.Stream;
import com.example.configservice.entities.Subject;
import com.example.configservice.services.ClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/config/class")
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','TEACHER')")
public class ClassController {

    ClassService classService;

     public ClassController( ClassService classService){
         this.classService=classService;
     }

     @PostMapping("/add")
     @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','TEACHER')")
    public ResponseEntity<ClassCreationResponseDto> createClassRoute(
            @RequestBody ClassCreationDto classCreationDto
    ){
        Class createdClass=classService.createClass(classCreationDto);
        ClassCreationResponseDto response=new ClassCreationResponseDto();
        response.setName(createdClass.getName());
        response.setMessage("Class created");

        return ResponseEntity.ok(response);
    }

    // Get all classes
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','TEACHER')")
    public List<Class> getAllClasses() {
        return classService.getClasses();
    }

    // Get a specific class by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','TEACHER')")
    public Optional<Class> getClassById(@PathVariable Integer id) {
        return classService.getClassById(id);
    }

    // Create a new class
//    @PostMapping
//    public Class createClass(@RequestBody Class clazz) {
//        return classService.createClass(clazz);
//    }

    // Update an existing class
//    @PutMapping("/{id}")
//    public Class updateClass(@PathVariable Integer id, @RequestBody Class clazz) {
//        // Ensure that the id from the path is set on the entity
//        clazz.setId(id);
//        return classService.updateClass(clazz);
//    }

    // Delete a class by ID
    @DeleteMapping("/{id}")
    public void deleteClass(@PathVariable Integer id) {
        classService.deleteClass(id);
    }

    // Get streams associated with a given class
    @GetMapping("/{id}/streams")
    public Set<Stream> getStreamsForClass(@PathVariable Integer id) {
        return classService.getStreamsForClass(id);
    }

    public Class updateClass(@PathVariable Integer id, @RequestBody Class clazz) {
        // Ensure that the id from the path is set on the entity
        clazz.setId(id);
        return classService.updateClass(clazz);
    }

    // Get subjects associated with a given class
    @GetMapping("/{id}/subjects")
    public Set<Subject> getSubjectsForClass(@PathVariable Integer id) {
        return classService.getSubjectsForClass(id);
    }

    // Add a subject to a class
    @PostMapping("/{classId}/subjects/{subjectId}")
    public Class addSubjectToClass(@PathVariable Integer classId, @PathVariable Integer subjectId) {
        return classService.addSubjectToClass(classId, subjectId);
    }

    // Remove a subject from a class
    @DeleteMapping("/{classId}/subjects/{subjectId}")
    public Class removeSubjectFromClass(@PathVariable Integer classId, @PathVariable Integer subjectId) {
        return classService.removeSubjectFromClass(classId, subjectId);
    }

    //update the grading for a subject in a class
//    @PutMapping("/{classId}/subjects/{subjectId}/grading/{gradingId}")
//    public Class updateGradingForSubject(
//            @RequestBody ClassSubjectGradeDto classSubjectGradeDto ) {
//
//        return classService.updateGradingForSubject(
//                classSubjectGradeDto.getClassId(),
//                classSubjectGradeDto.getSubjectId(),
//                classSubjectGradeDto.getGradingId()
//        );
//    }

}
