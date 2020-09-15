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
  private TashUser users;

  @Column(name = "confirmed_at")
  private Timestamp confirmedAt;

  @Column(name = "completed_at")
  private Timestamp completedAt;



  public void updateDataAssignment(Assignment assignment) {
    this.id = assignment.id;
    this.workOrder = assignment.workOrder;
    this.users = assignment.users;
    this.confirmedAt = assignment.confirmedAt;
    this.completedAt = assignment.completedAt;



  }


}



