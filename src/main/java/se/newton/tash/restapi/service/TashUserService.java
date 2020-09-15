package se.newton.tash.restapi.service;

import se.newton.tash.restapi.model.TashUser;

import java.util.List;

public interface TashUserService {
  List<TashUser> fetchAllUsers();
  TashUser fetchUserOrNullById(Long id);
  TashUser fetchUserOrExceptionById(Long id);
  TashUser createNewUser(TashUser newTashUser);
  TashUser updateExistingUserOrNull(TashUser newTashUserData);
  TashUser updateExistingUserOrException(TashUser newTashUserData);
  TashUser deleteUserOrNullById(Long id);
  TashUser deleteUserOrExceptionById(Long id);
  TashUser findByTokenOrNull(String token);
  String login(String email, String password);
}
