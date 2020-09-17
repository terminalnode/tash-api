package se.newton.tash.restapi.service;

import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.model.Token;

public interface TokenService {
  Token createTokenOrNull(TashUser user);
  Token createTokenOrException(TashUser user);
  Token fetchTokenOrNull(String token);
}
