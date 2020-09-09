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
}
