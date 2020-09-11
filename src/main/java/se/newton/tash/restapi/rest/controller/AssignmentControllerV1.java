package se.newton.tash.restapi.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.tash.restapi.model.Assignment;
import se.newton.tash.restapi.repository.AssignmentRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/assignments")

public class AssignmentControllerV1 {


    @Autowired
    AssignmentRepository assignmentRepository;

    @GetMapping
    public List<Assignment> getAllAssignments(){
        return assignmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Assignment getAssignmentById(@PathVariable long id) {
        return assignmentRepository.getOne(id);
    }

    @PostMapping
    public void createNewAssignment (@RequestBody Assignment assignment) {
        assignment.setId(0);
        System.out.println(assignment.toString());
        assignmentRepository.save(assignment);

    }

    @PutMapping
    public Assignment updateExistingAssignment(@RequestBody Assignment updatedAssignment) {
        Optional<Assignment> assignment = assignmentRepository.findById(updatedAssignment.getId());

        if (assignment.isPresent()) {
            Assignment assignmentWithNewData = assignment.get();
            assignmentWithNewData.updateDataAssignment(updatedAssignment);
            return assignmentRepository.save(assignmentWithNewData);
        } else {
            throw new IllegalArgumentException("The requested assignment does not exist");

        }
    }


    @DeleteMapping("{id}")
    public Assignment deleteAssignmentById(@PathVariable Long id) {
        Optional<Assignment> assignment = assignmentRepository.findById(id);

        if ((assignment.isPresent())) {
            assignmentRepository.delete(assignment.get());
            return assignment.get();
        }   else {
            throw new IllegalArgumentException("The assignment does not exist.");

        }
    }
}
