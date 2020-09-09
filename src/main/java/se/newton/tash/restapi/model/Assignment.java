package se.newton.tash.restapi.model;

import lombok.Data;

import javax.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "assignments")
public @Data class Assignment {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "work_order_id")
  private WorkOrder workOrder;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User users;

  @Column(name = "confirmed_at")
  private Timestamp confirmedAt;

  @Column(name = "completed_at")
  private Timestamp completedAt;


}
