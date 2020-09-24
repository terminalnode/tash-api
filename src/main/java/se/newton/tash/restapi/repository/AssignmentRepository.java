package se.newton.tash.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.newton.tash.restapi.model.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

}
