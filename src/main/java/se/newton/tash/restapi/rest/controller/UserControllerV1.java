package se.newton.tash.restapi.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserControllerV1 {
  @GetMapping
  public String helloWorld() {
    return "Hello world!";
  }

  @GetMapping("HEJ_IGEN")
  public String helloWorld2() {
    return "Hello world, AGAIN! WOW!!!!!";
  }
}