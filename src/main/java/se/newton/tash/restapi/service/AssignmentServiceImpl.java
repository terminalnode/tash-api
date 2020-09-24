package se.newton.tash.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.newton.tash.restapi.model.Assignment;
import se.newton.tash.restapi.repository.AssignmentRepository;
import se.newton.tash.restapi.rest.exceptions.assignmentexceptions.AssignmentIdNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

  @Autowired
  AssignmentRepository assignmentRepository;

  public List<Assignment> getAllAssignments() {
    return assignmentRepository.findAll();
  }

  public Assignment getAssignmentById(long id) {
    if (assignmentRepository.existsById(id)) {
      return assignmentRepository.findById(id).get();
    } else {
      throw new AssignmentIdNotFoundException(
          String.format("Assignment with id %d not found", id)
      );
    }
  }

  public Assignment createNewAssignment(Assignment assignment) {
    assignment.setId(0L);
    return assignmentRepository.save(assignment);
  }

  public Assignment updateExistingAssignment(Assignment assignment) {
    if (assignmentRepository.existsById(assignment.getId())) {
      return assignmentRepository.save(assignment);
    } else {
      throw new AssignmentIdNotFoundException(
          String.format("Assignment with id %dd not found", assignment.getId())
      );
    }
  }

  public Assignment deleteAssignmentById(long id) {
    Optional<Assignment> assignment = assignmentRepository.findById(id);

    if (assignment.isPresent()) {
      assignmentRepository.delete(assignment.get());
      return assignment.get();
    } else {
      throw new AssignmentIdNotFoundException(
          String.format("Assignment with id %ddd not found", id)
      );
    }
  }
}