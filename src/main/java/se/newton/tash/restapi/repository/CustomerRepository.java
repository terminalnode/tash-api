package se.newton.tash.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.newton.tash.restapi.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
