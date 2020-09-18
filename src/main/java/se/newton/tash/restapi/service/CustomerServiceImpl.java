package se.newton.tash.restapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.newton.tash.restapi.model.Customer;
import se.newton.tash.restapi.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  CustomerRepository customerRepository;


  @Override
  public List<Customer> fetchAllCustomers() {
    return customerRepository.findAll();
  }

  @Override
  public Customer fetchCustomerByIdOrNull(Long id) {
    return customerRepository.findById(id)
        .orElse(null);
  }

  @Override
  public Customer fetchCustomerByIdOrException(Long id) {
    return customerRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("The specified Customer does not exist"));
  }

  @Override
  public Customer createNewCustomer(Customer newCustomer) {
    newCustomer.setId(0L);
    System.out.println(newCustomer.toString());
    return customerRepository.save(newCustomer);
  }

  @Override
  public Customer updateExistingCustomerOrNull(Customer updateCustomer) {
    Optional<Customer> customer = customerRepository.findById(updateCustomer.getId());
    customer.ifPresent(c -> c.updateDataWithCustomer(updateCustomer));
    return customer.map(value -> customerRepository.save(value))
        .orElse(null);
  }

  @Override
  public Customer updateExistingCustomerOrException(Customer updateCustomer) {
    Customer customer = customerRepository.findById(updateCustomer.getId())
        .orElseThrow(() -> new IllegalArgumentException("This Customer couldn't be updated"));

    customer.updateDataWithCustomer(updateCustomer);
    return customerRepository.save(customer);
  }

  @Override
  public Customer deleteCustomerByIdOrNull(Long id) {
    Optional<Customer> customer = customerRepository.findById(id);
    customer.ifPresent(c -> customerRepository.delete(c));
    return customer.orElse(null);
  }

  @Override
  public Customer deleteCustomerByIdOrException(Long id) {
    Optional<Customer> customer = customerRepository.findById(id);
    customer.ifPresent(c -> customerRepository.delete(c));
    return customer.orElseThrow(() -> new IllegalArgumentException("This customer does not exist"));
  }


}