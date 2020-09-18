package se.newton.tash.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.model.Token;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

  @Autowired
  TashUserService tashUserService;

  @Autowired
  TokenService tokenService;

  @Override
  public String login(String email, String password) {
    TashUser user = tashUserService.validateEmailAndPasswordOrNull(email, password);
    return Optional.ofNullable(tokenService.createTokenOrException(user))
        .filter(t -> t.getToken() != null && !t.getToken().isBlank())
        .map(Token::getToken)
        .orElseThrow(() -> new BadCredentialsException("Something went wrong when logging in."));
  }

}
