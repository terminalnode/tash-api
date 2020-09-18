package se.newton.tash.restapi.rest.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import se.newton.tash.restapi.rest.errorresponses.GenericErrorResponse;
import se.newton.tash.restapi.rest.exceptions.GenericTashException;


public abstract class GenericRestExceptionHandler {
  protected ResponseEntity<GenericErrorResponse> responseEntityGenerator(
      GenericTashException exc,
      HttpStatus status) {

    GenericErrorResponse error = new GenericErrorResponse();
    error.setMessage(exc.getMessage());
    error.setTimeStamp(System.currentTimeMillis());
    error.setInternalName(exc.getInternalName());
    error.setStatus(status.value());
    return new ResponseEntity<>(error, status);
  }
}
