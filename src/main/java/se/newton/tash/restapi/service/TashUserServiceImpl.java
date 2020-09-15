package se.newton.tash.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.repository.TashUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TashUserServiceImpl implements TashUserService {
  @Autowired
  private TashUserRepository tashUserRepository;

  @Override
  public List<TashUser> fetchAllUsers() {
    return tashUserRepository.findAll();
  }

  @Override
  public TashUser fetchUserOrNullById(Long id) {
    return tashUserRepository.findById(id)
        .orElse(null);
  }

  @Override
  public TashUser fetchUserOrExceptionById(Long id) {
    return tashUserRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("The specified user does not exist"));
  }

  @Override
  public TashUser createNewUser(TashUser newTashUser) {
    newTashUser.setId(0L);
    return tashUserRepository.save(newTashUser);
  }

  @Override
  public TashUser updateExistingUserOrNull(TashUser newTashUserData) {
    Optional<TashUser> user = tashUserRepository.findById(newTashUserData.getId());
    user.ifPresent(u -> u.updateDataWithUser(newTashUserData));
    return user.map(value -> tashUserRepository.save(value))
        .orElse(null);
  }

  @Override
  public TashUser updateExistingUserOrException(TashUser newTashUserData) {
    TashUser tashUser = tashUserRepository.findById(newTashUserData.getId())
        .orElseThrow(() -> new IllegalArgumentException("The specified user does not exist"));

    tashUser.updateDataWithUser(newTashUserData);
    return tashUserRepository.save(tashUser);
  }

  @Override
  public TashUser deleteUserOrNullById(Long id) {
    Optional<TashUser> user = tashUserRepository.findById(id);
    user.ifPresent(u -> tashUserRepository.delete(u));
    return user.orElse(null);
  }

  @Override
  public TashUser deleteUserOrExceptionById(Long id) {
    TashUser tashUser = tashUserRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("The specified user does not exist"));

    tashUserRepository.delete(tashUser);
    return tashUser;
  }

  @Override
  public Optional<User> findByToken(String token) {
    Optional<TashUser> optionalUser = tashUserRepository.findByToken(token);
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
    TashUser tashUser = tashUserRepository.findByEmailAndPassword(email, password);
    if (tashUser != null) {
      String token = UUID.randomUUID().toString();
      tashUser.setToken(token);
      tashUserRepository.save(tashUser);
      return token;
    }
    
    return "";
  }
}