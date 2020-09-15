package se.newton.tash.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.newton.tash.restapi.model.TashUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TashUser, Long> {
  TashUser findByEmailAndPassword(String email, String password);
  Optional<TashUser> findByToken(String token);
}