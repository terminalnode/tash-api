package se.newton.tash.restapi.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.newton.tash.restapi.service.TashUserService;

@RestController
@RequestMapping("token")
public class TokenControllerV1 {
  @Autowired
  private TashUserService tashUserService;

  @PostMapping
  public String getToken(
      @RequestParam("email") String email,
      @RequestParam("password") String password)
  {
    String token = tashUserService.login(email, password);
    if (token == null || token.isBlank()) {
      throw new IllegalArgumentException("Invalid username or password");
    }

    return token;
  }
}
