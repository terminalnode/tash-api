package se.newton.tash.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.newton.tash.restapi.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmailAndPassword(String email, String password);
  Optional<User> findByToken(String token);
}