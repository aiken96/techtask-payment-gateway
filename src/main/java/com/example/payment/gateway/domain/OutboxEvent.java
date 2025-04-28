package com.example.payment.gateway.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter @Setter
@NoArgsConstructor
public class OutboxEvent {
  @Id
  @GeneratedValue
  private Long id;
  private Long paymentId;
  private Long orderId;
  private PaymentStatus status;
  private Long amount;
  private Instant ts = Instant.now();
  private boolean published = false;

  public OutboxEvent(Long id, Long orderId, PaymentStatus status, Long amount) {
    this.id = id;
    this.orderId = orderId;
    this.status = status;
    this.amount = amount;
  }
}