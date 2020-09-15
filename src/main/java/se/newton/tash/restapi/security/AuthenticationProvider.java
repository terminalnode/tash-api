package se.newton.tash.restapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.repository.TashUserRepository;

import java.util.List;
import java.util.Optional;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
  @Autowired
  TashUserRepository tashUserRepository;

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
    TashUser user = Optional
        .ofNullable(usernamePasswordAuthenticationToken.getCredentials())
        .map(String::valueOf)
        .flatMap(tashUserRepository::findByToken)
        .orElseThrow(() -> new BadCredentialsException("Invalid token"));

    // TODO We can conditionally add authorities to this list. Probably.
    List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER");

    return new User(
        user.getEmail(), user.getPassword(),
        true, true,
        true, true,
        authorities
    );
  }
}
