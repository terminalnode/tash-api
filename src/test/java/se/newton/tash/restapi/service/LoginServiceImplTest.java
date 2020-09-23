package se.newton.tash.restapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.model.Token;
import se.newton.tash.restapi.repository.TashUserRepository;
import se.newton.tash.restapi.repository.TokenRepository;
import se.newton.tash.restapi.rest.controller.LoginControllerV1;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoginServiceImplTest {

  @InjectMocks
  LoginServiceImpl loginService;

  @Mock
  TokenService tokenService;

  @Mock
  TashUserService userService;

  Token.TokenBuilder tokenBuilder;

  String userTokenString;
  Token userToken;

  @BeforeEach
  public void setUp() {
   TashUser u1 = TashUser.builder()
       .id(997L)
       .email("Test2@gmail.com")
       .firstName("Testy2")
       .lastName("Testsson2")
       .admin(false)
       .longitude(0.2)
       .latitude(0.9)
       .avatarUrl("https://www.testySite.se/testies.png")
       .password("Test2")
       .build();

    tokenBuilder = Token.builder();
    userTokenString = "HERRO-IM-RUuupaa";
    userToken = tokenBuilder.admin(false).user(u1).token(userTokenString).build();

    when(userService.validateEmailAndPasswordOrNull(any(), any())).thenReturn(null);
  }

  @Test
  public void login_whenTokenServiceCreateTokenReturnsNull_throwException() {
    when(tokenService.createTokenOrNull(any())).thenReturn(null);
    BadCredentialsException test = Assertions.assertThrows(
        BadCredentialsException.class,
        () -> loginService.login("TEST", "TEST")
    );

    assertThat(test.getMessage()).isEqualTo("Something went wrong when logging in.");
  }

  @Test
  public void login_whenTokenServiceCreateTokenReturnsToken_returnTokenString() {
    when(tokenService.createTokenOrNull(any())).thenReturn(userToken);
    String token = loginService.login("Test", "Test");

    assertThat(token).isEqualTo(userTokenString);
  }
}