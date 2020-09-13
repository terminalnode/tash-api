package se.newton.tash.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import se.newton.tash.restapi.model.User;
import se.newton.tash.restapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    if (id == null) {
      return null;
    }
    
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
    User user = fetchUserOrNullById(newUserData.getId());
    if (user == null) {
      return null;
    }

    user.updateDataWithUser(newUserData);
    return userRepository.save(user);
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

  @Override
  public User deleteUserOrNullById(Long id) {
    User user = fetchUserOrNullById(id);
    if (user == null) {
      return null;
    }

    userRepository.delete(user);
    return user;
  }

  @Override
  public User deleteUserOrExceptionById(Long id) {
    User user = fetchUserOrNullById(id);
    if (user == null) {
      throw new IllegalArgumentException("The specified user does not exist.");
    }

    userRepository.delete(user);
    return user;
  }

  @Override
  public Optional<org.springframework.security.core.userdetails.User> findByToken(String token) {
    Optional<User> optionalUser = userRepository.findByToken(token);
    if (optionalUser.isPresent()) {
      User dbUser = optionalUser.get();
      org.springframework.security.core.userdetails.User user =
          new org.springframework.security.core.userdetails.User(
              dbUser.getEmail(), dbUser.getPassword(),
              true, true, true, true,
              AuthorityUtils.createAuthorityList("USER")
          );
      return Optional.of(user);
    }
    return Optional.empty();
  }

  @Override
  public String login(String email, String password) {
    User user = userRepository.findByEmailAndPassword(email, password);
    if (user != null) {
      String token = UUID.randomUUID().toString();
      user.setToken(token);
      userRepository.save(user);
      return token;
    }
    
    return "";
  }
}
