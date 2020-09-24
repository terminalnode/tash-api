package se.newton.tash.restapi.dbseeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.newton.tash.restapi.model.Customer;
import se.newton.tash.restapi.repository.CustomerRepository;

@Component
public class CustomerSeeder {
  @Autowired
  CustomerRepository customerRepository;

  public void seed() {
    customerRepository.save(new Customer(
        0L,
        "Frontedge IT",
        "frontedge@email.com",
        "The best consulting company."));
    customerRepository.save(new Customer(
        0L,
        "Alten Consulting",
        "alten@email.com",
        "An acceptable consulting company."));
    customerRepository.save(new Customer(
        0L,
        "Tolw",
        "tolw@email.com",
        "One stop hockey shop for all your hockey needs."));
  }
}
