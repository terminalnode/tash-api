package se.newton.tash.restapi.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.User;
import se.newton.tash.restapi.repository.UserRepository;
import se.newton.tash.restapi.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerV1Test {

  @InjectMocks
  UserControllerV1 userController;

  @Mock
  UserService userService;

  @Mock
  UserRepository userRepository;

  User u1, u2, u3;
  User.UserBuilder newUserBuilder;
  List<User> allUsers;
  
  @BeforeEach
  public void setUp() {
    User.UserBuilder userBuilder = User.builder()
        .lastName("Lastnamingson").longitude(0.1).latitude(0.2).password("SuperSecretPassword");
    u1 = userBuilder.id(1L).firstName("User 1").admin(true).avatarUrl("http://www.heh.se/bild.png").build();
    u2 = userBuilder.id(2L).firstName("User 2").admin(false).avatarUrl("http://www.lol.se/bild.jpg").build();
    u3 = userBuilder.id(13L).firstName("User 13").admin(false).avatarUrl("http://www.fniss.se/bild.jpg").build();
    allUsers = new ArrayList<>();
    allUsers.add(u1);
    allUsers.add(u2);
    allUsers.add(u3);
    
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

    when(userRepository.findById(any())).thenReturn(Optional.empty());
    when(userRepository.findById(1L)).thenReturn(Optional.of(u1));
    when(userRepository.findById(2L)).thenReturn(Optional.of(u2));
    when(userRepository.findById(13L)).thenReturn(Optional.of(u3));
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