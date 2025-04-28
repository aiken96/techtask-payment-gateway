package com.example.payment.gateway.domain;

import java.time.Instant;

public record PaymentEvent(Long paymentId,
                           Long orderId,
                           PaymentStatus status,
                           Long amount,
                           Instant ts) {}
