package se.newton.tash.restapi.rest.controller;


import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.tash.restapi.model.Customer;
import se.newton.tash.restapi.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerControllerV1 {
  @Autowired
  private CustomerRepository customerRepository;

  @GetMapping
  public List<Customer> fetchAllCustomers() {
    return customerRepository.findAll();
  }

  @GetMapping("/{id}")
  public Customer fetchCustomerById(@PathVariable Long id) {
    Optional<Customer> customer = customerRepository.findById(id);

    if(customer.isPresent()){
      return customer.get();
    } else {
      throw new IllegalArgumentException("The requested customer does not exist");
    }
  }

  @PostMapping
  public Customer createNewCustomer(@RequestBody Customer newCustomer) {
    newCustomer.setId(0L);
    return customerRepository.save(newCustomer);
  }

  @PutMapping
  public Customer updateExistingCustomer(@RequestBody Customer updatedCustomer) {
    Optional<Customer> customer = customerRepository.findById(updatedCustomer.getId());

    if (customer.isPresent()) {
      Customer customerWithNewData = customer.get();
      customerWithNewData.updateDataWithCustomer(updatedCustomer);
      return customerRepository.save(customerWithNewData);
    } else {
      throw new IllegalArgumentException("The requested customer does not exist");
    }
  }

  @DeleteMapping("{id}")
  public Customer deleteCustomerById(@PathVariable Long id){
    Optional<Customer> customer = customerRepository.findById(id);

    if (customer.isPresent()) {
      customerRepository.delete(customer.get());
      return customer.get();
    } else {
      throw new IllegalArgumentException("The requested customer does not exist");
    }
  }
}
