package se.newton.tash.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.newton.tash.restapi.model.User;
import se.newton.tash.restapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public List<User> fetchAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User fetchUserOrNullById(Long id) {
    if (id == null) return null;
    
    Optional<User> fetchedUser = userRepository.findById(id);
    return fetchedUser.orElse(null);
  }

  @Override
  public User fetchUserOrExceptionById(Long id) {
    User user = fetchUserOrNullById(id);
    if (user == null) {
      throw new IllegalArgumentException("The requested user does not exist.");
    }
    return user;
  }
}
