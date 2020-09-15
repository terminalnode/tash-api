package se.newton.tash.restapi.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.service.TashUserService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class TashUserControllerV1Test {

  @InjectMocks
  UserControllerV1 userController;

  @Mock
  TashUserService tashUserService;

  TashUser.TashUserBuilder newUserBuilder;

  @BeforeEach
  public void setUp() {
    newUserBuilder = TashUser.builder()
        .id(-1L)
        .email("lotta.shmotta@burgerking.se")
        .firstName("Lotta")
        .lastName("Shmotta")
        .admin(false)
        .longitude(0.1)
        .latitude(0.9)
        .avatarUrl("https://www.burgerking.se/burgerbuilder2000.png")
        .password("burger4lifexoxoxo");
  }

  @Test
  public void fetchAllUsers_always_callsUserServiceFetchAllUsers() {
    userController.fetchAllUsers();
    verify(tashUserService, times(1))
        .fetchAllUsers();
  }

  @Test
  public void fetchUserById_always_callsUserServiceFetchUserOrExceptionById() {
    userController.fetchUserById(-100L);
    verify(tashUserService, times(1))
        .fetchUserOrExceptionById(-100L);
  }

  @Test
  public void createNewUser_always_callsUserServiceCreateNewUser() {
    TashUser newTashUser = newUserBuilder.build();
    userController.createNewUser(newTashUser);
    verify(tashUserService, times(1))
        .createNewUser(newTashUser);
  }

  @Test
  public void updateExistingUser_always_callsUserServiceUpdateExistingUserOrException() {
    TashUser tashUser = newUserBuilder.id(666L).build();
    userController.updateExistingUser(tashUser);
    verify(tashUserService, times(1))
        .updateExistingUserOrException(tashUser);
  }

  @Test
  public void deleteUserById_always_callsUserServiceDeleteUserOrExceptionById() {
    userController.deleteUserById(666L);
    verify(tashUserService, times(1))
        .deleteUserOrExceptionById(666L);
  }
}