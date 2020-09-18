package se.newton.tash.restapi.service;

import se.newton.tash.restapi.model.Customer;

import java.util.List;

public interface CustomerService {
  List<Customer> fetchAllCustomers();
  Customer fetchCustomerByIdOrNull(Long id);
  Customer fetchCustomerByIdOrException(Long id);
  Customer createNewCustomer(Customer customer);
  Customer updateExistingCustomerOrNull(Customer customer);
  Customer updateExistingCustomerOrException(Customer customer);
  Customer deleteCustomerByIdOrNull(Long id);
  Customer deleteCustomerByIdOrException(Long id);

}
