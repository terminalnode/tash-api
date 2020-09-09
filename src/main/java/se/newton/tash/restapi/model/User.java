package se.newton.tash.restapi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
public @Data class User {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @Column(name = "email")
  private String email;

  @Column(name = "first_name")
  private String firstName;
  
  @Column(name = "last_name")
  private String lastName;
  
  @Column(name = "is_admin")
  private boolean admin;

  @Column(name = "longitude")
  private double longitude;
  
  @Column(name = "latitude")
  private double latitude;
  
  @Column(name = "avatar_url")
  private String avatarUrl;
}
