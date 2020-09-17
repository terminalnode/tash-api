package se.newton.tash.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tash_users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class TashUser {
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
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Boolean admin;

  @Column(name = "longitude")
  private Double longitude;
  
  @Column(name = "latitude")
  private Double latitude;
  
  @Column(name = "avatar_url")
  private String avatarUrl;

  @Column(name = "password")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY
  )
  @JsonIgnore
  List<Token> tokens;

  @Column(name = "token")
  @JsonIgnore
  private String token;

  /**
   * Take another User object as only argument and replace the values of the fields in
   * this object with this the values of the field in that one. Skip sensitive fields
   * such as id and password.
   * @param tashUser The object with which to update the fields in this object.
   */
  public void updateDataWithUser(TashUser tashUser) {
    this.email = tashUser.email;
    this.firstName = tashUser.firstName;
    this.lastName = tashUser.lastName;
    this.admin = tashUser.admin;
    this.longitude = tashUser.longitude;
    this.latitude = tashUser.latitude;
    this.avatarUrl = tashUser.avatarUrl;

    // Skip this data:
    // this.id = user.id;
    // this.password = user.password;
  }
}
