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
    return userRepository.findById(id)
        .orElse(null);
  }

  @Override
  public User fetchUserOrExceptionById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("The specified user does not exist"));
  }

  @Override
  public User createNewUser(User newUser) {
    newUser.setId(0L);
    return userRepository.save(newUser);
  }

  @Override
  public User updateExistingUserOrNull(User newUserData) {
    Optional<User> user = userRepository.findById(newUserData.getId());
    user.ifPresent(u -> u.updateDataWithUser(newUserData));
    return user.map(value -> userRepository.save(value))
        .orElse(null);
  }

  @Override
  public User updateExistingUserOrException(User newUserData) {
    User user = userRepository.findById(newUserData.getId())
        .orElseThrow(() -> new IllegalArgumentException("The specified user does not exist"));

    user.updateDataWithUser(newUserData);
    return userRepository.save(user);
  }

  @Override
  public User deleteUserOrNullById(Long id) {
    Optional<User> user = userRepository.findById(id);
    user.ifPresent(u -> userRepository.delete(u));
    return user.orElse(null);
  }

  @Override
  public User deleteUserOrExceptionById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("The specified user does not exist"));

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