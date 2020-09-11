package se.newton.tash.restapi.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.tash.restapi.model.User;
import se.newton.tash.restapi.repository.UserRepository;
import se.newton.tash.restapi.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @GetMapping
  public List<User> fetchAllUsers() {
    return userService.fetchAllUsers();
  }

  @GetMapping("/{id}")
  public User fetchUserById(@PathVariable Long id) {
    return userService.fetchUserOrExceptionById(id);
  }

  @PostMapping
  public User createNewUser(@RequestBody User newUser) {
    return userService.createNewUser(newUser);
  }

  @PutMapping
  public User updateExistingUser(@RequestBody User updatedUser) {
    return userService.updateExistingUserOrException(updatedUser);
  }

  @DeleteMapping("{id}")
  public User deleteUserById(@PathVariable Long id) {
    Optional<User> user = userRepository.findById(id);

    if (user.isPresent()) {
      userRepository.delete(user.get());
      return user.get();
    } else {
      throw new IllegalArgumentException("The requested user does not exist.");
    }
  }
}