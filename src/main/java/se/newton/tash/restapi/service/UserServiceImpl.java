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

  @Override
  public User createNewUser(User newUser) {
    newUser.setId(0L);
    return userRepository.save(newUser);
  }

  @Override
  public User updateExistingUserOrNull(User newUserData) {
    if (newUserData.getId() == null) return null;

    Optional<User> optDbUser = userRepository.findById(newUserData.getId());
    if (optDbUser.isEmpty()) {
      return null;
    } else {
      User dbUser = optDbUser.get();
      dbUser.updateDataWithUser(newUserData);
      return userRepository.save(dbUser);
    }
  }

  @Override
  public User updateExistingUserOrException(User newUserData) {
    if (newUserData.getId() == null) {
      throw new IllegalArgumentException("No ID specified, can not update user.");
    }
    
    Optional<User> optDbUser = userRepository.findById(newUserData.getId());
    User dbUser = optDbUser
        .orElseThrow(() -> new IllegalArgumentException("The specified user does not exist."));

    dbUser.updateDataWithUser(newUserData);
    return userRepository.save(dbUser);
  }
}
