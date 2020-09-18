package se.newton.tash.restapi.rest.exceptions;

public class GenericTashException extends RuntimeException {
  String internalName;

  public GenericTashException(String message, String internalName) {
    super(message);
    this.internalName = internalName;
  }

  public String getInternalName() {
    return internalName;
  }
}

