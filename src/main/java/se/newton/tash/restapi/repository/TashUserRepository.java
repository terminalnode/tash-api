package se.newton.tash.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.newton.tash.restapi.model.TashUser;

import java.util.Optional;

@Repository
public interface TashUserRepository extends JpaRepository<TashUser, Long> {
  Optional<TashUser> findByEmail(String email);
  Optional<TashUser> findByToken(String token);
}