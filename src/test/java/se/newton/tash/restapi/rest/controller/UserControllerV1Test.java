package se.newton.tash.restapi.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.User;
import se.newton.tash.restapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserControllerV1Test {

  @InjectMocks
  UserControllerV1 userController;

  @Mock
  UserRepository userRepository;
  
  User u1, u2, u3;
  List<User> allUsers;
  
  @BeforeEach
  public void setUp() {
    u1 = new User(1L, "User 1", true, "http://www.heh.se/bild.png");
    u2 = new User(2L, "User 2", false, "http://www.lol.se/bild.jpg");
    u3 = new User(13L, "User 13", false, "http://www.fniss.se/bild.jpg");
    allUsers = new ArrayList<>();
    allUsers.add(u1);
    allUsers.add(u2);
    allUsers.add(u3);

    when(userRepository.findAll()).thenReturn(allUsers);
    when(userRepository.findById(1L)).thenReturn(Optional.of(u1));
    when(userRepository.findById(2L)).thenReturn(Optional.of(u2));
    when(userRepository.findById(13L)).thenReturn(Optional.of(u3));
  }

  @Test
  public void testFetchAllUsers() {
    List<User> result = userController.fetchAllUsers();
    assertThat(result).contains(u1);
  }
}