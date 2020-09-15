package se.newton.tash.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.newton.tash.restapi.model.TashUser;
import se.newton.tash.restapi.repository.TashUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TashUserServiceImpl implements TashUserService {
  @Autowired
  TashUserRepository tashUserRepository;

  @Autowired
  PasswordEncoder passwordEncoder;


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
    String encryptedPassword = passwordEncoder.encode(newTashUser.getPassword());
    newTashUser.setPassword(encryptedPassword);
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
  public TashUser findByTokenOrNull(String token) {
    return tashUserRepository.findByToken(token).orElse(null);
  }

  @Override
  public String login(String email, String password) {
    Optional<TashUser> tashUser = tashUserRepository.findByEmail(email);
    boolean passwordCorrect = tashUser
        .map(u -> passwordEncoder.matches(password, u.getPassword()))
        .orElse(false);

    if (passwordCorrect) {
      TashUser user = tashUser.get();
      String token = UUID.randomUUID().toString();
      user.setToken(token);

      tashUserRepository.save(user);
      return token;
    }
    
    return "";
  }
}