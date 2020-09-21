package se.newton.tash.restapi.rest.exceptions.workorderexceptions;

import se.newton.tash.restapi.rest.exceptions.GenericTashException;

public class WorkOrderIdNotFoundException extends GenericTashException {

  public WorkOrderIdNotFoundException(String errorMessage) { super(errorMessage, "WorkOrderIdNotFoundException"); }

}
