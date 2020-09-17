package se.newton.tash.restapi.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.model.Token;
import se.newton.tash.restapi.service.TashUserService;
import se.newton.tash.restapi.service.TokenService;

import java.util.Optional;

@RestController
@RequestMapping("token")
public class TokenControllerV1 {
  @Autowired
  TashUserService tashUserService;

  @Autowired
  TokenService tokenService;

  @PostMapping
  public String createToken(
      @RequestParam("email") String email,
      @RequestParam("password") String password
  ) {
    TashUser user = tashUserService.validateEmailAndPasswordOrNull(email, password);
    return Optional.ofNullable(tokenService.createTokenOrException(user))
        .filter(t -> t.getToken() != null && !t.getToken().isBlank())
        .map(Token::getToken)
        .orElseThrow(() -> new BadCredentialsException("Something went wrong when logging in."));
  }
}
