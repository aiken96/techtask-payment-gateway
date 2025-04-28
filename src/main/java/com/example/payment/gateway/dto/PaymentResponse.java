package com.example.payment.gateway.dto;


import com.example.payment.gateway.domain.PaymentStatus;

public record PaymentResponse(Long paymentId, PaymentStatus status) {}
