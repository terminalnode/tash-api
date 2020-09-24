package se.newton.tash.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "assignments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class Assignment {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "work_order_id")
  private WorkOrder workOrder;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private TashUser users;

  @Column(name = "confirmed_at")
  private Timestamp confirmedAt;

  @Column(name = "completed_at")
  private Timestamp completedAt;




  }





