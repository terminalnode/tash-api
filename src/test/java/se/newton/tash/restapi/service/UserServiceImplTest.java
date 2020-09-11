package se.newton.tash.restapi.service;

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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserServiceImplTest {
  @InjectMocks
  UserServiceImpl userService;

  @Spy
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

    when(userRepository.findAll()).thenReturn(allUsers);
    when(userRepository.findById(any())).thenReturn(Optional.empty());
    when(userRepository.findById(u1.getId())).thenReturn(Optional.of(u1));
    when(userRepository.findById(u2.getId())).thenReturn(Optional.of(u2));
    when(userRepository.findById(u3.getId())).thenReturn(Optional.of(u3));
  }

  @Test
  public void fetchAllUsers_whenAllUsersAreListOfThree_returnsThatList() {
    List<User> result = userService.fetchAllUsers();
    assertThat(result.size()).isEqualTo(3);
    assertThat(result)
        .contains(u1)
        .contains(u2)
        .contains(u3);
  }

  @Test
  public void fetchUserOrNullById_whenUserDoesNotExist_returnsNull() {
    User result = userService.fetchUserOrNullById(100L);
    assertThat(result).isEqualTo(null);
  }

  @Test
  public void fetchUserOrNullById_whenUserDoesExist_returnsUser() {
    User result = userService.fetchUserOrNullById(u1.getId());
    assertThat(result).isEqualTo(u1);
  }

  @Test
  public void fetchUserOrExceptionById_whenUserDoesNotExist_throwsException() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> userService.fetchUserOrExceptionById(100L)
    );
  }
  
  @Test
  public void fetchUserOrExceptionById_whenUserDoesExist_returnsUser() {
    User result = userService.fetchUserOrExceptionById(u1.getId());
    assertThat(result).isEqualTo(u1);
  }

  @Test
  public void createNewUser_withSpecifiedUserId_createsUserWithUserIdZero() {
    // Create new user with controller.
    newUserBuilder.id(666L);
    User newUser = newUserBuilder.build();
    User twinUser = newUserBuilder.build();
    userService.createNewUser(newUser);

    // Verify that exactly one user was created and capture that user.
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository, times(1))
        .save(userCaptor.capture());
    User savedUser = userCaptor.getValue();

    // Verify that user was saved with identical user info, except for ID.
    // Any ID >= 0 is accepted, to allow for different implementations.
    assertThat(savedUser).isNotEqualTo(twinUser);
    assertThat(savedUser.getId()).isGreaterThanOrEqualTo(0);
    twinUser.setId(savedUser.getId());
    assertThat(savedUser).isEqualTo(twinUser);
  }
}
