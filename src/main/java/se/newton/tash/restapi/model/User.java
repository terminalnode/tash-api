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

  @Column(name = "password")
  private String password;

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
