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
    newUser.setId(0L);
    return userRepository.save(newUser);
  }

  @PutMapping
  public User updateExistingUser(@RequestBody User updatedUser) {
    Optional<User> user = userRepository.findById(updatedUser.getId());
    
    if (user.isPresent()) {
      User userWithNewData = user.get();
      userWithNewData.updateDataWithUser(updatedUser);
      return userRepository.save(userWithNewData);
    } else {
      throw new IllegalArgumentException("The requested user does not exist.");
    }
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