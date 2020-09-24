package se.newton.tash.restapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  AuthenticationProvider provider;

  private static final RequestMatcher NO_PRIVS_REQUIRED = new OrRequestMatcher(
      new AntPathRequestMatcher("/token/**"),
      new AntPathRequestMatcher("/api/v*/security/none")
  );

  private static final RequestMatcher BASE_REQUIRED = new OrRequestMatcher(
      new AntPathRequestMatcher("/api/**")
  );

  private static final RequestMatcher ADMIN_REQUIRED = new OrRequestMatcher(
      new AntPathRequestMatcher("/api/v*/security/admin"),
      new AntPathRequestMatcher("/api/v*/users/")
  );

  public SecurityConfiguration(AuthenticationProvider authenticationProvider) {
    super();
    this.provider = authenticationProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(provider);
  }

  @Override
  public void configure(WebSecurity webSecurity) throws Exception {
    // TODO This doesn't seem to work properly yet. When there's overlap between this and the one below the below one wins
    webSecurity
        .ignoring()
        .requestMatchers(NO_PRIVS_REQUIRED);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
          .exceptionHandling()
        .and()
          .authenticationProvider(provider)
          .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
          .authorizeRequests()
          .requestMatchers(ADMIN_REQUIRED)
          .hasAuthority("ADMIN")
        .and()
          .authenticationProvider(provider)
          .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
          .authorizeRequests()
          .requestMatchers(BASE_REQUIRED)
          .hasAuthority("BASE")
        .and()
          .csrf().disable()
          .formLogin().disable()
          .httpBasic().disable()
          .logout().disable();
  }

  @Bean
  AuthenticationFilter authenticationFilter() throws Exception {
    final AuthenticationFilter filter = new AuthenticationFilter(BASE_REQUIRED);
    filter.setAuthenticationManager(authenticationManager());
    //filter.setAuthenticationSuccessHandler(successHandler());
    return filter;
  }

  @Bean
  AuthenticationEntryPoint forbiddenEntryPoint() {
    return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
