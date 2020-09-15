package se.newton.tash.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TashUserServiceImpl implements TashUserService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public List<TashUser> fetchAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public TashUser fetchUserOrNullById(Long id) {
    return userRepository.findById(id)
        .orElse(null);
  }

  @Override
  public TashUser fetchUserOrExceptionById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("The specified user does not exist"));
  }

  @Override
  public TashUser createNewUser(TashUser newTashUser) {
    newTashUser.setId(0L);
    return userRepository.save(newTashUser);
  }

  @Override
  public TashUser updateExistingUserOrNull(TashUser newTashUserData) {
    Optional<TashUser> user = userRepository.findById(newTashUserData.getId());
    user.ifPresent(u -> u.updateDataWithUser(newTashUserData));
    return user.map(value -> userRepository.save(value))
        .orElse(null);
  }

  @Override
  public TashUser updateExistingUserOrException(TashUser newTashUserData) {
    TashUser tashUser = userRepository.findById(newTashUserData.getId())
        .orElseThrow(() -> new IllegalArgumentException("The specified user does not exist"));

    tashUser.updateDataWithUser(newTashUserData);
    return userRepository.save(tashUser);
  }

  @Override
  public TashUser deleteUserOrNullById(Long id) {
    Optional<TashUser> user = userRepository.findById(id);
    user.ifPresent(u -> userRepository.delete(u));
    return user.orElse(null);
  }

  @Override
  public TashUser deleteUserOrExceptionById(Long id) {
    TashUser tashUser = userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("The specified user does not exist"));

    userRepository.delete(tashUser);
    return tashUser;
  }

  @Override
  public Optional<User> findByToken(String token) {
    Optional<TashUser> optionalUser = userRepository.findByToken(token);
    if (optionalUser.isPresent()) {
      TashUser dbTashUser = optionalUser.get();
      User user =
          new User(
              dbTashUser.getEmail(), dbTashUser.getPassword(),
              true, true, true, true,
              AuthorityUtils.createAuthorityList("USER")
          );
      return Optional.of(user);
    }
    return Optional.empty();
  }

  @Override
  public String login(String email, String password) {
    TashUser tashUser = userRepository.findByEmailAndPassword(email, password);
    if (tashUser != null) {
      String token = UUID.randomUUID().toString();
      tashUser.setToken(token);
      userRepository.save(tashUser);
      return token;
    }
    
    return "";
  }
}