package se.newton.tash.restapi.rest.errorresponses;

public class GenericErrorResponse {
  private int status;
  private String message;
  private long timeStamp;
  private String internalName;

  //----- Constructors -----//
  public GenericErrorResponse() {
    // empty no-arg constructor
  }

  public GenericErrorResponse(int status, String message, long timeStamp, String internalName) {
    this.status = status;
    this.message = message;
    this.timeStamp = timeStamp;
    this.internalName = internalName;
  }

  //----- Getters and setters -----//
  public void setStatus(int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public void setInternalName(String internalName) {
    this.internalName = internalName;
  }

  public String getInternalName() {
    return internalName;
  }
}