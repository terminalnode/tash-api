package se.newton.tash.restapi.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.service.TashUserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {
  @Autowired
  private TashUserService tashUserService;

  @GetMapping
  public List<TashUser> fetchAllUsers() {
    return tashUserService.fetchAllUsers();
  }

  @GetMapping("/{id}")
  public TashUser fetchUserById(@PathVariable Long id) {
    return tashUserService.fetchUserOrExceptionById(id);
  }

  @PostMapping
  public TashUser createNewUser(@RequestBody TashUser newTashUser) {
    return tashUserService.createNewUser(newTashUser);
  }

  @PutMapping
  public TashUser updateExistingUser(@RequestBody TashUser updatedTashUser) {
    return tashUserService.updateExistingUserOrException(updatedTashUser);
  }

  @DeleteMapping("{id}")
  public TashUser deleteUserById(@PathVariable Long id) {
    return tashUserService.deleteUserOrExceptionById(id);
  }
}