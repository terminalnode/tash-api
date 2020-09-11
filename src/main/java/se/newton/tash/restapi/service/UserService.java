package se.newton.tash.restapi.service;

import se.newton.tash.restapi.model.User;

import java.util.List;

public interface UserService {
  List<User> fetchAllUsers();
  User fetchUserOrNullById(Long id);
  User fetchUserOrExceptionById(Long id);
  User createNewUser(User newUser);
}
