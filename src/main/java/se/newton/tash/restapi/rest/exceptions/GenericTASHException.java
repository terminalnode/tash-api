package se.newton.tash.restapi.rest.exceptions;

public class GenericTASHException extends RuntimeException {
  String internalName;

  public GenericTASHException(String message, String internalName) {
    super(message);
    this.internalName = internalName;
  }

  public String getInternalName() {
    return internalName;
  }
}

