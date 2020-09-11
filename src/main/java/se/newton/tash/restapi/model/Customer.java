package se.newton.tash.restapi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public @Data class Customer {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "description")
  private String description;


  public Customer (Long id, String name, String descrption) {
    this.id = id;
    this.name = name;
    this.description = descrption;
  }

  public void updateDataWithCustomer(Customer customer) {
    this.name = customer.name;
    this.email = customer.email;
    this.description = customer.description;

  }
}


