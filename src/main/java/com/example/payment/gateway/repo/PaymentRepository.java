package com.example.payment.gateway.repo;

import com.example.payment.gateway.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
