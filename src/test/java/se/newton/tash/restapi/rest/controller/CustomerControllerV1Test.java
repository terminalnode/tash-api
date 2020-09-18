package se.newton.tash.restapi.rest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.Customer;
import se.newton.tash.restapi.repository.CustomerRepository;
import se.newton.tash.restapi.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerControllerV1Test {

  @InjectMocks
  CustomerControllerV1 customerController;

  @Mock
  CustomerService customerService;

  Customer.CustomerBuilder customerBuilder;

  @BeforeEach
  public void setUp() {
    customerBuilder = Customer.builder()
        .id(-1L)
        .name("TestJocke")
        .email("testJocke@Mail.com")
        .description("test test test");
  }

  @Test
  public void fetchAllCustomer_callsCustomerServiceFetchAllCustomers() {
    customerController.fetchAllCustomers();
    verify(customerService,times(1))
        .fetchAllCustomers();
  }

  @Test
  public void fetchCustomerById_callsCustomerServiceOrException() {
    customerController.fetchCustomerById(666L);
    verify(customerService, times(1))
        .fetchCustomerByIdOrException(666L);
  }

  @Test
  public void createNewCustomer_callsCustomerServiceCreateNewCustomerOrException() {
    Customer customer = customerBuilder.build();
    customerController.createNewCustomer(customer);
    verify(customerService, times(1))
        .createNewCustomer(customer);
  }

  @Test
  public void updateExistingCustomer_callsCustomerServiceUpdateExistingCustomerOrException() {
    Customer customer = customerBuilder.id(665L).build();
    customerController.updateExistingCustomer(customer);
    verify(customerService, times(1))
        .updateExistingCustomerOrException(customer);
  }

  @Test
  public void deleteCustomerById_callsCustomerServiceDeleteCustomerOrException() {
    customerController.deleteCustomerById(666L);
    verify(customerService, times(1))
        .deleteCustomerByIdOrException(666L);
  }



}