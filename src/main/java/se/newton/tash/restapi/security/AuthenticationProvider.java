package se.newton.tash.restapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.model.Token;
import se.newton.tash.restapi.service.TokenService;

import java.util.List;
import java.util.Optional;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
  @Autowired
  TokenService tokenService;

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
    Token token = Optional
        .ofNullable(usernamePasswordAuthenticationToken.getCredentials())
        .map(String::valueOf)
        .map(tokenService::fetchTokenOrNull)
        .orElseThrow(() -> new BadCredentialsException("Invalid token"));

    TashUser user = Optional
        .ofNullable(token.getUser())
        .orElseThrow(() -> new BadCredentialsException("Could not find token's associated user"));

    // All users have the BASE privileges, administrators have the ADMIN privilege
    List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("BASE");
    if (token.getAdmin()) {
      authorities.add(new SimpleGrantedAuthority("ADMIN"));
    }

    return new User(
        user.getEmail(), user.getPassword(),
        true, true,
        true, true,
        authorities
    );
  }
}
