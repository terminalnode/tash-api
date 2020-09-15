package se.newton.tash.restapi.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.Customer;
import se.newton.tash.restapi.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CustomerControllerV1Test {

  @InjectMocks
  CustomerControllerV1 customerController;

  @Mock
  CustomerRepository customerRepository;

  Customer.CustomerBuilder newCustomerBuilder;

  Customer c1, c2, c3;
  List<Customer> allCustomers;

  @BeforeEach
  public void setUp() {
    newCustomerBuilder = Customer.builder()
        .id(-1L)
        .name("TestJocke")
        .email("testJocke@Mail.com")
        .description("test test test");

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
  public void testFetchAllCustomers(){
    List<Customer> result = customerController.fetchAllCustomers();
    Assertions.assertEquals(3, result.size());
    assertThat(result)
        .contains(c1)
        .contains(c2)
        .contains(c3);
  }

  @Test
  public void testFetchCustomerById1(){
    Customer result = customerController.fetchCustomerById(-2L);
    Assertions.assertEquals(c1,result);
  }

  @Test
  public void testFetchCustomerById2(){
    Customer result = customerController.fetchCustomerById(-3L);
    Assertions.assertEquals(c2,result);
  }

  @Test
  public void testFetchCustomerById3(){
    Customer result = customerController.fetchCustomerById(-4L);
    Assertions.assertEquals(c3,result);
  }

  @Test
  public void testCreateNewCustomer(){
    Customer.CustomerBuilder customerBuilder = Customer.builder()
        .id(-1L)
        .name("testJocke")
        .email("testJocke@Mail.com")
        .description("A new test has been created");

    Customer testCustomerCreation = customerBuilder.build();
    Customer notTestCustomerCreation = customerBuilder.build();

    customerController.createNewCustomer(testCustomerCreation);

    ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
    verify(customerRepository, times(1))
        .save(customerCaptor.capture());
    Customer savedCustomer = customerCaptor.getValue();

    assertThat(savedCustomer).isNotEqualTo(notTestCustomerCreation);
    assertThat(savedCustomer.getId()).isGreaterThanOrEqualTo(0);
    notTestCustomerCreation.setId(savedCustomer.getId());
    assertThat(savedCustomer).isEqualTo(notTestCustomerCreation);
  }

  @Test
  public void testEditExistingCustomer(){}

  @Test
  public void testDeleteExistingCustomer(){}





}