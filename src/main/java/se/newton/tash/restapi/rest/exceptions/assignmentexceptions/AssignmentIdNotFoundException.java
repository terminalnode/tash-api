package se.newton.tash.restapi.rest.exceptions.assignmentexceptions;

import se.newton.tash.restapi.rest.exceptions.GenericTashException;

public class AssignmentIdNotFoundException extends GenericTashException {

  public AssignmentIdNotFoundException(String errorMessage) { super(errorMessage, "AssignmentIdNotFoundException"); }

}
