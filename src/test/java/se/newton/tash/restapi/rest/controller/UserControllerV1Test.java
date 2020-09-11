package se.newton.tash.restapi.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.User;
import se.newton.tash.restapi.service.UserService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserControllerV1Test {

  @InjectMocks
  UserControllerV1 userController;

  @Mock
  UserService userService;

  User.UserBuilder newUserBuilder;

  @BeforeEach
  public void setUp() {
    newUserBuilder = User.builder()
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
    verify(userService, times(1))
        .fetchAllUsers();
  }

  @Test
  public void fetchUserById_always_callsUserServiceFetchUserOrExceptionById() {
    userController.fetchUserById(-100L);
    verify(userService, times(1))
        .fetchUserOrExceptionById(-100L);
  }

  @Test
  public void createNewUser_always_callsUserServiceCreateNewUser() {
    User newUser = newUserBuilder.build();
    userController.createNewUser(newUser);
    verify(userService, times(1))
        .createNewUser(newUser);
  }

  @Test
  public void updateExistingUser_always_callsUserServiceUpdateExistingUserOrException() {
    User user = newUserBuilder.id(666L).build();
    userController.updateExistingUser(user);
    verify(userService, times(1))
        .updateExistingUserOrException(user);
  }

  @Test
  public void deleteUserById_always_callsUserServiceDeleteUserOrExceptionById() {
    userController.deleteUserById(666L);
    verify(userService, times(1))
        .deleteUserOrExceptionById(666L);
  }
}