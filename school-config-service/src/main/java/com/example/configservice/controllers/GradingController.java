package com.example.configservice.controllers;

import com.example.configservice.entities.Grading;
import com.example.configservice.entities.Subject;
import com.example.configservice.services.GradingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/config/gradings")
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','TEACHER')")
@Tag(name = "Grading Management", description = "Endpoints for managing grading configurations")
public class GradingController {

    private final GradingService gradingService;

    @Autowired
    public GradingController(GradingService gradingService) {
        this.gradingService = gradingService;
    }

    // Get all gradings
    @Operation(summary = "Get all gradings", description = "Retrieves a list of all grading configurations.")
    @GetMapping
    public List<Grading> getAllGradings() {
        return gradingService.getAllGradings();
    }

    // Get a specific grading by ID
    @Operation(summary = "Get grading by ID", description = "Retrieves a specific grading configuration by its ID.")
    @GetMapping("/{id}")
    public Optional<Grading> getGradingById(@PathVariable Integer id) {
        return gradingService.getGradingById(id);
    }

    // Create a new grading
    @Operation(summary = "Create a new grading", description = "Creates a new grading configuration.")
    @PostMapping
    public Grading createGrading(@RequestBody Grading grading) {
        return gradingService.createGrading(grading);
    }

    // Update an existing grading
    @Operation(summary = "Update a grading", description = "Updates an existing grading configuration by ID.")
    @PutMapping("/{id}")
    public Grading updateGrading(@PathVariable Integer id, @RequestBody Grading grading) {
        grading.setId(id); // Ensure the grading has the correct ID before updating
        return gradingService.updateGrading(grading);
    }

    // Delete a grading by ID
    @Operation(summary = "Delete a grading", description = "Deletes a grading configuration by its ID.")
    @DeleteMapping("/{id}")
    public void deleteGrading(@PathVariable Integer id) {
        gradingService.deleteGrading(id);
    }

//    // Assign grading to subject
//    @Operation(summary = "Assign grading to a subject", description = "Assigns a specific grading to a subject by IDs.")
//    @PostMapping("/assign/{subjectId}/{gradeId}")
//    public Subject assignGradingToSubject(@PathVariable Integer subjectId, @PathVariable Integer gradeId) {
//        return gradingService.assignGradingToSubject(subjectId, gradeId);
//    }
}
