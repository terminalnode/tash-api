package se.newton.tash.restapi.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
  AuthenticationFilter (final RequestMatcher requiresAuth) {
    super(requiresAuth);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse
  ) throws AuthenticationException, IOException, ServletException {
    String authHeader = httpServletRequest.getHeader(AUTHORIZATION);
    String token = (authHeader != null && !authHeader.isBlank()) ?
        authHeader.replaceAll("^Bearer ", "").strip() : "";

    Authentication requestAuthentication = new UsernamePasswordAuthenticationToken(token, token);
    return getAuthenticationManager().authenticate(requestAuthentication);
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain,
      Authentication authentication
  ) throws IOException, ServletException {
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
