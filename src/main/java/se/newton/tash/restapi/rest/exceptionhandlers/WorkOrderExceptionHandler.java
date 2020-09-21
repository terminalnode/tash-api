package se.newton.tash.restapi.rest.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.newton.tash.restapi.rest.errorresponses.GenericErrorResponse;
import se.newton.tash.restapi.rest.exceptions.workorderexceptions.WorkOrderIdNotFoundException;

public class WorkOrderExceptionHandler extends GenericRestExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(WorkOrderIdNotFoundException exc) {
      return responseEntityGenerator(exc, HttpStatus.BAD_REQUEST);
  }
}
