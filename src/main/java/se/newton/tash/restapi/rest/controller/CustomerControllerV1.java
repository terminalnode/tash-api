package se.newton.tash.restapi.rest.controller;


import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.tash.restapi.model.Customer;
import se.newton.tash.restapi.repository.CustomerRepository;
import se.newton.tash.restapi.service.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerControllerV1 {
  @Autowired
  private CustomerService customerService;

  @GetMapping
  public List<Customer> fetchAllCustomers() {
    return customerService.fetchAllCustomers();
  }

  @GetMapping("/{id}")
  public Customer fetchCustomerById(@PathVariable Long id) {
    return customerService.fetchCustomerByIdOrException(id);
  }

  @PostMapping
  public Customer createNewCustomer(@RequestBody Customer newCustomer) {
    return customerService.createNewCustomer(newCustomer);
  }

  @PutMapping
  public Customer updateExistingCustomer(@RequestBody Customer updatedCustomer) {
    return customerService.updateExistingCustomerOrException(updatedCustomer);
  }

  @DeleteMapping("{id}")
  public Customer deleteCustomerById(@PathVariable Long id) {
    return customerService.deleteCustomerByIdOrException(id);
  }
}