package com.example.payment.gateway.repo;

import com.example.payment.gateway.domain.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {
  List<OutboxEvent> findByPublishedFalse();
}