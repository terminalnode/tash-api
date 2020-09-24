package se.newton.tash.restapi.service;

import se.newton.tash.restapi.model.Assignment;

import java.util.List;

public interface AssignmentService {

  public List<Assignment> getAllAssignments();
  public Assignment getAssignmentById(long id);
  public Assignment createNewAssignment(Assignment assignment);
  public Assignment updateExistingAssignment(Assignment assignment);
  public Assignment deleteAssignmentById(long id);


}
