package se.newton.tash.restapi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
public @Data class User {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "email")
  private String email;

  @Column(name = "first_name")
  private String firstName;
  
  @Column(name = "last_name")
  private String lastName;
  
  @Column(name = "is_admin")
  private Boolean admin;

  @Column(name = "longitude")
  private Double longitude;
  
  @Column(name = "latitude")
  private Double latitude;
  
  @Column(name = "avatar_url")
  private String avatarUrl;

  @Column(name = "password")
  private String password;

  /**
   * Constructor useful for tests.
   */
  public User(Long id, String firstName, Boolean admin, String avatarUrl) {
    this.id = id;
    this.firstName = firstName;
    this.admin = admin;
    this.avatarUrl = avatarUrl;

    this.lastName = "Lastnamingson";
    this.longitude = 0.1;
    this.latitude = 0.2;
    this.password = "Super-Secret-Password-123";
  }
  
  /**
   * Take another User object as only argument and replace the values of the fields in
   * this object with this the values of the field in that one. Skip sensitive fields
   * such as id and password.
   * @param user The object with which to update the fields in this object.
   */
  public void updateDataWithUser(User user) {
    this.email = user.email;
    this.firstName = user.firstName;
    this.lastName = user.lastName;
    this.admin = user.admin;
    this.longitude = user.longitude;
    this.latitude = user.latitude;
    this.avatarUrl = user.avatarUrl;

    // Skip this data:
    // this.id = user.id;
    // this.password = user.password;
  }
}
