package se.newton.tash.restapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import se.newton.tash.restapi.service.TashUserService;

import java.util.Optional;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
  @Autowired
  TashUserService tashUserService;

  @Override
  protected void additionalAuthenticationChecks(
      UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication
  ) throws AuthenticationException {
    // Empty
  }

  @Override
  protected UserDetails retrieveUser(
      String username,
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
  ) throws AuthenticationException {
    Object token = usernamePasswordAuthenticationToken.getCredentials();
    
    return Optional
        .ofNullable(token)
        .map(String::valueOf)
        .flatMap(tashUserService::findByToken)
        .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token" + token));
  }
}
