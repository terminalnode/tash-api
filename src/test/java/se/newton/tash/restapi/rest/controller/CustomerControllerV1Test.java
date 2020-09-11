package se.newton.tash.restapi.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.sun.xml.bind.v2.TODO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.Customer;
import se.newton.tash.restapi.model.User;
import se.newton.tash.restapi.repository.CustomerRepository;
import se.newton.tash.restapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CustomerControllerV1Test {

  @InjectMocks
  CustomerControllerV1 customerController;

  @Mock
  CustomerRepository customerRepository;

  Customer c1, c2, c3;
  List<Customer> allCustomers;

  @BeforeEach
  public void setUp() {
    c1 = new Customer(1L, "F-Jocke", "vill köpa bröd");
    c2 = new Customer(2L, "H-Jocke", "vill byta korv och bröd");
    c3 = new Customer(3L, "K-Jocke", "vill köpa korv");
    allCustomers = new ArrayList<>();
    allCustomers.add(c1);
    allCustomers.add(c2);
    allCustomers.add(c3);

    when(customerRepository.findAll()).thenReturn(allCustomers);
    when(customerRepository.findById(1L)).thenReturn(Optional.of(c1));
    when(customerRepository.findById(2L)).thenReturn(Optional.of(c2));
    when(customerRepository.findById(3L)).thenReturn(Optional.of(c3));
  }

  // write test for true and false.

  @Test
  public void testFetchAllCustomers(){}

  @Test
  public void testFetchCustomerById(){}

  @Test
  public void testCreateNewCustomer(){}

  @Test
  public void testEditExistingCustomer(){}

  @Test
  public void testDeleteExistingCustomer(){}





}