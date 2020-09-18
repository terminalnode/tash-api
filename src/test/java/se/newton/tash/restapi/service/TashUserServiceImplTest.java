package se.newton.tash.restapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.repository.TashUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TashUserServiceImplTest {
  @InjectMocks
  TashUserServiceImpl userService;

  @Mock
  TashUserRepository tashUserRepository;

  @Mock
  PasswordEncoder passwordEncoder;

  TashUser u1, u2, u3;
  TashUser.TashUserBuilder newUserBuilder;
  List<TashUser> allTashUsers;
  String encryptedPassword;

  @BeforeEach
  public void setUp() {
    newUserBuilder = TashUser.builder()
        .firstName("Firstname").lastName("Lastnamingson").admin(false)
        .email("mycoolemail@email.com")
        .longitude(0.1).latitude(0.2).password("SuperSecretPassword")
        .avatarUrl("wwww.coolabilder.se/minBild.jpg");
    u1 = newUserBuilder.id(1L).firstName("User 1").admin(true).avatarUrl("http://www.heh.se/bild.png").build();
    u2 = newUserBuilder.id(2L).firstName("User 2").admin(false).avatarUrl("http://www.lol.se/bild.jpg").build();
    u3 = newUserBuilder.id(13L).firstName("User 13").admin(false).avatarUrl("http://www.fniss.se/bild.jpg").build();

    // Change UserBuilder field values to make sure they're different from above.
    newUserBuilder
        .firstName("New Firstname").lastName("New Lastnamingson").admin(true)
        .longitude(0.3).latitude(0.4).password("NewSuperSecretPassword")
        .avatarUrl("wwww.fulabilder.se/minBild.jpg");

    when(tashUserRepository.findById(any())).thenReturn(Optional.empty());
    when(tashUserRepository.findByEmail(any())).thenReturn(Optional.empty());
    when(tashUserRepository.findById(u1.getId())).thenReturn(Optional.of(u1));
    when(tashUserRepository.findByEmail(u1.getEmail())).thenReturn(Optional.of(u1));

    encryptedPassword = "CorrectHorseBatteryStapler";
    when(passwordEncoder.encode(any())).thenReturn(encryptedPassword);
    when(passwordEncoder.matches(eq(encryptedPassword), any())).thenReturn(true);
  }

  @Test
  public void fetchAllUsers_whenAllUsersAreListOfThree_returnsThatList() {
    allTashUsers = new ArrayList<>();
    allTashUsers.add(u1);
    allTashUsers.add(u2);
    allTashUsers.add(u3);
    when(tashUserRepository.findAll()).thenReturn(allTashUsers);

    List<TashUser> result = userService.fetchAllUsers();
    assertThat(result.size()).isEqualTo(3);
    assertThat(result)
        .contains(u1)
        .contains(u2)
        .contains(u3);
  }

  @Test
  public void fetchUserOrNullById_whenUserDoesNotExist_returnsNull() {
    TashUser result = userService.fetchUserOrNullById(100L);
    assertThat(result).isEqualTo(null);
  }

  @Test
  public void fetchUserOrNullById_whenUserDoesExist_returnsUser() {
    TashUser result = userService.fetchUserOrNullById(u1.getId());
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
    TashUser result = userService.fetchUserOrExceptionById(u1.getId());
    assertThat(result).isEqualTo(u1);
  }

  @Test
  public void createNewUser_withSpecifiedUserId_createsUserWithUserIdZero() {
    // Create new user with controller.
    newUserBuilder.id(666L);
    TashUser newTashUser = newUserBuilder.build();
    TashUser twinTashUser = newUserBuilder.build();
    userService.createNewUser(newTashUser);

    // Verify that exactly one user was created and capture that user.
    ArgumentCaptor<TashUser> userCaptor = ArgumentCaptor.forClass(TashUser.class);
    verify(tashUserRepository, times(1))
        .save(userCaptor.capture());
    TashUser savedTashUser = userCaptor.getValue();

    // Verify that user was saved with identical user info, except for ID.
    // Any ID >= 0 is accepted, to allow for different implementations.
    String savedPassword = savedTashUser.getPassword();
    String oldPassword = twinTashUser.getPassword();

    // Check password and ID
    assertThat(savedTashUser.getId()).isGreaterThanOrEqualTo(0);
    assertThat(savedPassword).isEqualTo(encryptedPassword);
    assertThat(savedPassword).isNotEqualTo(oldPassword);

    // Check that everything else is the same
    twinTashUser.setId(savedTashUser.getId());
    twinTashUser.setPassword(savedPassword);
    assertThat(savedTashUser).isEqualTo(twinTashUser);
  }
  
  @Test
  public void updateExistingUserOrNull_whenUserIdIsNull_returnsNull() {
    TashUser updatedTashUser = newUserBuilder.id(null).build();
    TashUser result = userService.updateExistingUserOrNull(updatedTashUser);
    assertThat(result).isEqualTo(null);
  }

  @Test
  public void updateExistingUserOrNull_whenUserIdDoesNotExist_returnsNull() {
    TashUser updatedTashUser = newUserBuilder.id(666L).build();
    TashUser result = userService.updateExistingUserOrNull(updatedTashUser);
    assertThat(result).isEqualTo(null);
  }

  @Test
  public void updateExistingUserOrNull_whenUserIdDoes_returnsUpdatedUser() {
    // Create new user with controller.
    TashUser newTashUserData = newUserBuilder.id(u1.getId()).build();
    TashUser twinTashUserData = newUserBuilder.id(u1.getId()).build();
    userService.updateExistingUserOrNull(newTashUserData);

    // Verify that exactly one user was created and capture that user.
    ArgumentCaptor<TashUser> userCaptor = ArgumentCaptor.forClass(TashUser.class);
    verify(tashUserRepository, times(1))
        .save(userCaptor.capture());
    TashUser savedTashUser = userCaptor.getValue();

    // Verify that user was saved with identical user info, except for password.
    // Password can only be updated separately.
    assertThat(savedTashUser).isNotEqualTo(twinTashUserData);
    assertThat(savedTashUser.getPassword()).isEqualTo(u1.getPassword());
    twinTashUserData.setPassword(u1.getPassword());
    assertThat(savedTashUser).isEqualTo(twinTashUserData);
  }

  @Test
  public void updateExistingUserOrException_whenUserIdIsNull_throwsException() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> userService.updateExistingUserOrException(newUserBuilder.id(null).build())
    );
  }

  @Test
  public void updateExistingUserOrException_whenUserIdDoesNotExist_throwsException() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> userService.updateExistingUserOrException(newUserBuilder.id(666L).build())
    );
  }

  @Test
  public void updateExistingUserOrException_whenUserIdDoesExist_returnsUpdatedUser() {
    TashUser newTashUserData = newUserBuilder.id(u1.getId()).build();
    TashUser twinTashUserData = newUserBuilder.id(u1.getId()).build();
    userService.updateExistingUserOrException(newTashUserData);

    // Verify that exactly one user was created and capture that user.
    ArgumentCaptor<TashUser> userCaptor = ArgumentCaptor.forClass(TashUser.class);
    verify(tashUserRepository, times(1))
        .save(userCaptor.capture());
    TashUser savedTashUser = userCaptor.getValue();

    // Verify that user was saved with identical user info, except for password.
    // Password can only be updated separately.
    assertThat(savedTashUser).isNotEqualTo(twinTashUserData);
    assertThat(savedTashUser.getPassword()).isEqualTo(u1.getPassword());
    twinTashUserData.setPassword(u1.getPassword());
    assertThat(savedTashUser).isEqualTo(twinTashUserData);
  }

  @Test
  public void deleteUserOrNullById_whenUserIdIsNull_returnsNull() {
    TashUser result = userService.deleteUserOrNullById(null);
    assertThat(result).isEqualTo(null);
  }

  @Test
  public void deleteUserOrNullById_whenUserIdDoesNotExist_returnsNull() {
    TashUser result = userService.deleteUserOrNullById(666L);
    assertThat(result).isEqualTo(null);
  }

  @Test
  public void deleteUserOrNullById_whenUserIdDoes_callsUserRepositoryDeleteAndReturnsUser() {
    // Try deleting u1 by id
    userService.deleteUserOrNullById(u1.getId());

    // Verify that the delete call was done exactly once and capture the
    // user object with which it was called.
    ArgumentCaptor<TashUser> userCaptor = ArgumentCaptor.forClass(TashUser.class);
    verify(tashUserRepository, times(1))
        .delete(userCaptor.capture());
    TashUser deletedTashUser = userCaptor.getValue();

    // Verify that the correct user was deleted.
    assertThat(deletedTashUser).isEqualTo(u1);
  }

  @Test
  public void deleteUserOrExceptionById_whenUserIdIsNull_throwsExceptionAndDoesNotCallDelete() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> userService.deleteUserOrExceptionById(null)
    );
    verify(tashUserRepository, times(0))
        .delete(any());
  }

  @Test
  public void deleteUserOrExceptionById_whenUserIdDoesNotExist_throwsExceptionAndDoesNotCallDelete() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> userService.deleteUserOrExceptionById(666L)
    );
    verify(tashUserRepository, times(0))
        .delete(any());
  }

  @Test
  public void deleteUserOrExceptionById_whenUserIdDoes_callsUserRepositoryDeleteAndReturnsUser() {
    // Try deleting u1 by id
    userService.deleteUserOrExceptionById(u1.getId());
    
    // Verify that the delete call was done exactly once and capture the
    // user object with which it was called.
    ArgumentCaptor<TashUser> userCaptor = ArgumentCaptor.forClass(TashUser.class);
    verify(tashUserRepository, times(1))
        .delete(userCaptor.capture());
    TashUser deletedTashUser = userCaptor.getValue();

    // Verify that the correct user was deleted.
    assertThat(deletedTashUser).isEqualTo(u1);
  }

  @Test
  public void validateEmailAndPasswordOrNull_whenPasswordCorrect_returnsUser() {
    assertThat(userService.validateEmailAndPasswordOrNull(u1.getEmail(), encryptedPassword))
        .isEqualTo(u1);
  }

  @Test
  public void validateEmailAndPasswordOrNull_whenPasswordIncorrect_returnsNull() {
    assertThat(userService.validateEmailAndPasswordOrNull(u1.getEmail(), "some random string"))
        .isNull();
  }

  @Test
  public void validateEmailAndPasswordOrNull_whenUserDoesNotExist_returnsNull() {
    assertThat(userService.validateEmailAndPasswordOrNull("not a valid email", encryptedPassword))
        .isNull();
  }

  @Test
  public void validateEmailAndPasswordOrException_whenPasswordCorrect_returnsUser() {
    assertThat(userService.validateEmailAndPasswordOrException(u1.getEmail(), encryptedPassword))
        .isEqualTo(u1);
  }
  
  @Test
  public void validateEmailAndPasswordOrException_whenPasswordIncorrect_throwsException() {
    BadCredentialsException exc = Assertions.assertThrows(
        BadCredentialsException.class,
        () -> userService.validateEmailAndPasswordOrException(u1.getEmail(), "some random string")
    );
    assertThat(exc.getMessage()).isEqualTo("Invalid username or password");
  }

  @Test
  public void validateEmailAndPasswordOrException_whenUserDoesNotExist_throwsException() {
    BadCredentialsException exc = Assertions.assertThrows(
        BadCredentialsException.class,
        () -> userService.validateEmailAndPasswordOrException("not a valid email", encryptedPassword)
    );
    assertThat(exc.getMessage()).isEqualTo("Invalid username or password");
  }
}
