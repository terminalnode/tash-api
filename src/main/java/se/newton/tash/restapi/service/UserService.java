package se.newton.tash.restapi.service;

import se.newton.tash.restapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
  List<User> fetchAllUsers();
  User fetchUserOrNullById(Long id);
  User fetchUserOrExceptionById(Long id);
  User createNewUser(User newUser);
  User updateExistingUserOrNull(User newUserData);
  User updateExistingUserOrException(User newUserData);
  User deleteUserOrNullById(Long id);
  User deleteUserOrExceptionById(Long id);
  Optional<org.springframework.security.core.userdetails.User> findByToken(String token);
  String login(String email, String password);
}
