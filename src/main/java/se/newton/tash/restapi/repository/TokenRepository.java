package se.newton.tash.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.newton.tash.restapi.model.Token;

public interface TokenRepository extends JpaRepository<Token, String> {
}
