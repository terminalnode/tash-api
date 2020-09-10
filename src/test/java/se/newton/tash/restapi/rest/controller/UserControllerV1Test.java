package se.newton.tash.restapi.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.User;
import se.newton.tash.restapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerV1Test {

  @InjectMocks
  UserControllerV1 userController;

  @Spy
  UserRepository userRepository;
  
  User u1, u2, u3;
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

    when(userRepository.findAll()).thenReturn(allUsers);
    when(userRepository.findById(any())).thenReturn(Optional.empty());
    when(userRepository.findById(1L)).thenReturn(Optional.of(u1));
    when(userRepository.findById(2L)).thenReturn(Optional.of(u2));
    when(userRepository.findById(13L)).thenReturn(Optional.of(u3));
  }

  @Test
  public void testFetchAllUsers() {
    List<User> result = userController.fetchAllUsers();
    assertThat(result.size()).isEqualTo(3);
    assertThat(result)
        .contains(u1)
        .contains(u2)
        .contains(u3);
  }

  @Test
  public void testFetchUserByValidId() {
    User result = userController.fetchUserById(1L);
    assertThat(result)
        .isEqualTo(u1)
        .isNotEqualTo(u2)
        .isNotEqualTo(u3);
  }

  @Test
  public void testFetchUserByInvalidId() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> userController.fetchUserById(100L)
    );
  }

  @Test
  public void testCreateNewUser() {
    // Create new user with controller.
    User.UserBuilder userBuilder = User.builder()
        .id(100L)
        .email("lotta.shmotta@burgerking.se")
        .firstName("Lotta")
        .lastName("Shmotta")
        .admin(false)
        .longitude(0.1)
        .latitude(0.9)
        .avatarUrl("https://www.burgerking.se/burgerbuilder2000.png")
        .password("burger4lifexoxoxo");
    User newUser = userBuilder.build();
    User twinUser = userBuilder.build();
    userController.createNewUser(newUser);

    // Verify that exactly one user was created and capture that user.
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository, times(1))
        .save(userCaptor.capture());
    User savedUser = userCaptor.getValue();

    // Verify that user was saved with correct credentials.
    assertThat(savedUser).isNotEqualTo(twinUser);
    twinUser.setId(savedUser.getId());
    assertThat(savedUser).isEqualTo(twinUser);
  }
}