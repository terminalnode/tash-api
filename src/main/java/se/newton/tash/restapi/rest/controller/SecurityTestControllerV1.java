package se.newton.tash.restapi.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/security")
public class SecurityTestControllerV1 {
  @GetMapping("/none")
  public String hasNone() {
    return "You're a person that exists!!";
  }

  @GetMapping("/base")
  public String hasBase() {
    return "You have base privileges!";
  }

  @GetMapping("/admin")
  public String hasAdmin() {
    return "You have admin privileges!";
  }
}
