package se.newton.tash.restapi.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.service.LoginService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LoginControllerV1Test {

  @InjectMocks
  LoginControllerV1 loginController;

  @Mock
  LoginService loginService;

  TashUser.TashUserBuilder userBuilder;


  @BeforeEach
  public void setUp() {
    userBuilder = TashUser.builder()
        .id(996L)
        .email("Test@gmail.com")
        .firstName("Testy")
        .lastName("Testsson")
        .admin(false)
        .longitude(0.1)
        .latitude(0.8)
        .avatarUrl("https://www.testySite.se/testies.png")
        .password("Test");
  }


  @Test
  public void testLogin_callsLoginServiceOrThrowsException() {
    loginController.createToken("Test@gmail.com", "Test");
    verify(loginService,times(1)).login("Test@gmail.com", "Test");
  }

}