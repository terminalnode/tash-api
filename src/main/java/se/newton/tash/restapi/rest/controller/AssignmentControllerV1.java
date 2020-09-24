package se.newton.tash.restapi.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.tash.restapi.model.Assignment;
import se.newton.tash.restapi.service.AssignmentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assignments")

public class AssignmentControllerV1 {
    @Autowired
    AssignmentService assignmentService;

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/{id}")
    public Assignment getAssignmentById(@PathVariable long id) {
        return assignmentService.getAssignmentById(id);
    }

    @PostMapping
    public Assignment createNewAssignment (@RequestBody Assignment assignment) {
        return assignmentService.createNewAssignment(assignment);
    }

    @PutMapping
    public Assignment updateExistingAssignment(@RequestBody Assignment assignment) {
        return assignmentService.updateExistingAssignment(assignment);
    }

    @DeleteMapping("/{id}")
    public Assignment deleteAssignmentById(@PathVariable long id) {
        return assignmentService.deleteAssignmentById(id);
    }
}
