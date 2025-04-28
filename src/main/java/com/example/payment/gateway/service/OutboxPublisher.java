package com.example.payment.gateway.service;

import com.example.payment.gateway.domain.OutboxEvent;
import com.example.payment.gateway.domain.PaymentEvent;
import com.example.payment.gateway.repo.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {
  private final OutboxEventRepository repo;
  private final KafkaTemplate<String, PaymentEvent> kafka;

  @Scheduled(fixedDelay = 5000)
  public void publishPending() {
    List<OutboxEvent> evts = repo.findByPublishedFalse();
    evts.forEach(e -> {
      kafka.send("payment.events.v1", e.getPaymentId().toString(),
        new PaymentEvent(e.getPaymentId(), e.getOrderId(), e.getStatus(), e.getAmount(), e.getTs()));
      e.setPublished(true); repo.save(e);
    });
  }
}