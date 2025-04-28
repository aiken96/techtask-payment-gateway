package com.example.payment.gateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentChecker {
    @Value("${payment.fraud.threshold:900000}")
    private long threshold;

    public boolean isSuspicious(long amount) {
        return amount > threshold;
    }
}