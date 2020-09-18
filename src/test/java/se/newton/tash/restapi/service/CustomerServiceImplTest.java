package se.newton.tash.restapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import se.newton.tash.restapi.model.Customer;
import se.newton.tash.restapi.model.WorkOrder;
import se.newton.tash.restapi.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceImplTest {

  @InjectMocks
  CustomerServiceImpl customerService;

  @Mock
  CustomerRepository customerRepository;

  Customer c1,c2,c3;
  Customer.CustomerBuilder customerBuilder;
  List<Customer> allCustomers;


  @BeforeEach
  public void setup(){
    customerBuilder = Customer.builder()
        .name("Name.TEST")
        .email("MAIL@TEST.com")
        .description("Description Test");

    c1 = customerBuilder.id(1L).name("C1").email("C1@TEST.com").description("Test1").build();
    c2 = customerBuilder.id(2L).name("C2").email("C2@TEST.com").description("Test2").build();
    c3 = customerBuilder.id(3L).name("C3").email("C3@TEST.com").description("Test3").build();

    allCustomers = new ArrayList<>();
    allCustomers.add(c1);
    allCustomers.add(c2);
    allCustomers.add(c3);

    when(customerRepository.findAll()).thenReturn(allCustomers);

    when(customerRepository.findById(any())).thenReturn(Optional.empty());
    when(customerRepository.findById(c1.getId())).thenReturn(Optional.of(c1));
    when(customerRepository.findById(c2.getId())).thenReturn(Optional.of(c2));
    when(customerRepository.findById(c3.getId())).thenReturn(Optional.of(c3));

  }

  @Test
  public void testFetchAllCustomer() {
    List<Customer> results = customerService.fetchAllCustomers();
    Assertions.assertEquals(3,results.size());
    assertThat(results)
        .contains(c1)
        .contains(c2)
        .contains(c3);
  }

  @Test
  public void fetchCustomerById_whenCustomerExist_returnCustomer() {
    Customer result = customerService.fetchCustomerByIdOrNull(c1.getId());
    assertThat(result).isEqualTo(c1);
  }

  @Test
  public void fetchCustomerById_whenCustomerDoesNotExist_returnNull() {
    Customer result = customerService.fetchCustomerByIdOrNull(666L);
    assertThat(result).isEqualTo(null);
  }

  @Test
  public void fetchCustomerById_whenCustomerDoesNotExist_throwsException() {
    Customer result = customerService.fetchCustomerByIdOrException(c1.getId());
    assertThat(result).isEqualTo(c1);
  }

  @Test
  public void fetchCustomerById_whenCustomerDoesNotExist_throwException() {
   Assertions.assertThrows(IllegalArgumentException.class,
       () -> customerService.fetchCustomerByIdOrException(666L));
  }

  @Test
  public void testCreateNewCustomer() {

    customerBuilder.id(999L);
    Customer customer = customerBuilder.build();
    Customer notCustomer = customerBuilder.build();

    customerService.createNewCustomer(customer);

    ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
    verify(customerRepository,times(1))
        .save(customerArgumentCaptor.capture());
    Customer savedCustomer = customerArgumentCaptor.getValue();

    assertThat(savedCustomer).isNotEqualTo(notCustomer);
    assertThat(savedCustomer.getId()).isGreaterThanOrEqualTo(0);
    notCustomer.setId(savedCustomer.getId());
    assertThat(savedCustomer).isEqualTo(notCustomer);
  }

  @Test
  public void updateExistingCustomer_whenCustomerIsNull_returnNull() {
    Customer updatedCustomer =  customerBuilder.id(null).build();
    Customer result = customerService.updateExistingCustomerOrNull(updatedCustomer);
    assertThat(result).isEqualTo(null);
  }

  @Test
  public void updateExistingCustomer_whenCustomerIsNotNull_returnNull() {
    Customer updatedCustomer =  customerBuilder.id(666L).build();
    Customer result = customerService.updateExistingCustomerOrNull(updatedCustomer);
    assertThat(result).isEqualTo(null);
  }

  @Test
  public void updateExistingCustomerOrNull_whenCustomerIdExist_returnUpdatedCustomer() {
    Customer updatedCustomer = customerBuilder.id(c1.getId()).build();
    customerService.updateExistingCustomerOrNull(updatedCustomer);

    ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
    verify(customerRepository,times(1))
        .save(customerArgumentCaptor.capture());
    Customer savedCustomer = customerArgumentCaptor.getValue();

    assertThat(updatedCustomer).isEqualTo(savedCustomer);
  }

  @Test
  public void updateExistingCustomer_whenCustomerIdIsNull_throwsException() {
   Assertions.assertThrows(
       IllegalArgumentException.class,
       () -> customerService.updateExistingCustomerOrException(customerBuilder.id(null).build())
    );
  }

  @Test
  public void updateExistingCustomer_whenCustomerIdNotExist_throwsException() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> customerService.updateExistingCustomerOrException(customerBuilder.id(666L).build())
    );
  }

  @Test
  public void updateExistingCustomerOrException_whenCustomerIdExist_returnUpdatedCustomer() {
    Customer updatedCustomer = customerBuilder.id(c1.getId()).build();
    customerService.updateExistingCustomerOrException(updatedCustomer);

    ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
    verify(customerRepository,times(1))
        .save(customerArgumentCaptor.capture());
    Customer savedCustomer = customerArgumentCaptor.getValue();

    assertThat(updatedCustomer).isEqualTo(savedCustomer);
  }

  @Test
  public void deleteCustomerOrNull_WhenCustomerIdNull_returnNull() {
    Customer result = customerService.deleteCustomerByIdOrNull(null);
    assertThat(result).isEqualTo(null);
  }

  @Test
  public void deleteCustomerOrNull_WhenCustomerIdNotExist_returnNull() {
    Customer result = customerService.deleteCustomerByIdOrNull(666L);
    assertThat(result).isEqualTo(null);
  }

  @Test
  public void deleteCustomerOrNull_callsCustomerRepositoryDeleteAndReturnsCustomer() {
    customerService.deleteCustomerByIdOrNull(c1.getId());

    ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
    verify(customerRepository, times(1))
        .delete(argumentCaptor.capture());
    Customer deletedCustomer = argumentCaptor.getValue();

    assertThat(deletedCustomer).isEqualTo(c1);
  }

  @Test
  public void deleteCustomerOrException_whenCustomerIdNotExist_throwException(){
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> customerService.deleteCustomerByIdOrException(666L)
    );
    verify(customerRepository, times(0))
        .delete(any());
  }
  @Test
  public void deleteCustomerOrException_whenCustomerIdIsNull_throwException(){
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> customerService.deleteCustomerByIdOrException(null)
    );
    verify(customerRepository, times(0))
        .delete(any());
  }

  @Test
  public void deleteCustomerOrException_whenCustomerIdExist_callsCustomerRepositoryDeleteAndReturnsCustomer() {
    customerService.deleteCustomerByIdOrException(c1.getId());

    ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
    verify(customerRepository, times(1))
        .delete(argumentCaptor.capture());
    Customer deletedCustomer = argumentCaptor.getValue();

    assertThat(deletedCustomer).isEqualTo(c1);
  }

}

