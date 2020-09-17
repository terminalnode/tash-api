package se.newton.tash.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tokens")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class Token {
  @Id
  @Column(name = "token")
  String token;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  TashUser user;
  
  @Column(name = "is_admin")
  Boolean admin;
}
