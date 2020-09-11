package se.newton.tash.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.newton.tash.restapi.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

}
