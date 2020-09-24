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
    public List<Assignment> getAllAssignments(){ return assignmentService.getAllAssignments();
    }

    @GetMapping("/{id}")
    public Assignment getAssignmentById(@PathVariable long id) {
        return assignmentService.getAssignmentById(id);
    }

    @PostMapping
    public Assignment createNewAssignment (@RequestBody Assignment assignment) {
        assignment.setId(0);
        System.out.println(assignment.toString());
        Assignment newAssignment = assignmentService.createNewAssignment(assignment);
        return newAssignment;

    }

    @PutMapping
    public Assignment updateExistingAssignment(@RequestBody Assignment assignment) {
        Assignment updatedAssignment = assignmentService.updateExistingAssignment(assignment);
        return updatedAssignment;

    }

    @DeleteMapping("/{id}")
    public Assignment deleteAssignmentById(@PathVariable long id) {
        Assignment assignment = assignmentService.deleteAssignmentById(id);
        return assignment;
    }
}
