package se.newton.tash.restapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "work_orders")
public @Data class WorkOrder {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "longitude")
  private double longitude;

  @Column(name = "latitude")
  private double latitude;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Temporal(TemporalType.DATE)
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  @Column(name = "created_at")
  private Date createdAt;

  @Temporal(TemporalType.DATE)
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  @Column(name = "completed_at")
  private Date completedAt;

}
