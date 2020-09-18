package se.newton.tash.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.model.Token;
import se.newton.tash.restapi.repository.TokenRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
  @Autowired
  TokenRepository tokenRepository;

  @Override
  public Token createTokenOrNull(TashUser user) {
    if (user == null) {
      return null;
    }
    
    Token token = Token.builder()
        .token(UUID.randomUUID().toString())
        .admin(user.getAdmin())
        .user(user)
        .build();
    return tokenRepository.save(token);
  }

  @Override
  public Token createTokenOrException(TashUser user) {
    Token token = createTokenOrNull(user);
    if (token == null) {
      throw new BadCredentialsException("Invalid token");
    }
    
    return token;
  }

  @Override
  public Token fetchTokenOrNull(String token) {
    return tokenRepository.findById(token)
        .orElse(null);
  }

  public String invalidateToken(String token) {
    Optional<Token> optToken = tokenRepository.findById(token);
    optToken.ifPresent(tokenRepository::delete);
    return optToken
        .map(x -> "You have been logged out")
        .orElseThrow(() -> new BadCredentialsException("Specified token doesn't exist. Mission accomplished?"));
  }
}
