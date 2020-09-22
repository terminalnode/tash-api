package se.newton.tash.restapi.dbseeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.model.Token;
import se.newton.tash.restapi.repository.TashUserRepository;
import se.newton.tash.restapi.repository.TokenRepository;

import java.util.UUID;

@Component
public class TokenSeeder {
  @Autowired
  TokenRepository tokenRepository;

  @Autowired
  TashUserRepository tashUserRepository;

  public void seed() {
    tashUserRepository
        .findAll()
        .forEach(this::generateTokenForUser);
  }
  
  private void generateTokenForUser(TashUser user) {
    // Generate a predictable random-looking UUID from user email.
    String uuid = UUID.nameUUIDFromBytes(user.getEmail().getBytes()).toString();

    Token token = Token.builder()
        .user(user)
        .admin(user.getAdmin())
        .token(uuid)
        .build();
    tokenRepository.save(token);
  }
}
