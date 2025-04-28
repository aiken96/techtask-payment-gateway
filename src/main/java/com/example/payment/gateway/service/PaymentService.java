package com.example.payment.gateway.service;

import com.example.payment.gateway.domain.OutboxEvent;
import com.example.payment.gateway.domain.Payment;
import com.example.payment.gateway.domain.PaymentStatus;
import com.example.payment.gateway.dto.CaptureRequest;
import com.example.payment.gateway.dto.PaymentRequest;
import com.example.payment.gateway.dto.PaymentResponse;
import com.example.payment.gateway.repo.OutboxEventRepository;
import com.example.payment.gateway.repo.PaymentRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repo;
    private final OutboxEventRepository outbox;
    private final PaymentChecker fraud;

    @Timed("payment.create.duration")
    @Transactional
    public PaymentResponse create(PaymentRequest req) {
        Payment p = new Payment();
        p.setOrderId(req.orderId()); p.setAmount(req.amount());
        p.setStatus(fraud.isSuspicious(req.amount()) ? PaymentStatus.FAILED : PaymentStatus.AUTHORIZED);

        repo.save(p);
        OutboxEvent outboxEvent = new OutboxEvent(p.getId(), p.getOrderId(), p.getStatus(), p.getAmount());
        outbox.save(outboxEvent);
        return new PaymentResponse(p.getId(), p.getStatus());
    }

    @Transactional
    public PaymentResponse capture(Long id, CaptureRequest req) {
        Payment p = repo.findById(id)
                .orElseThrow();

        if (p.getStatus() != PaymentStatus.AUTHORIZED)
            throw new IllegalStateException();

        if (req.amount() < p.getAmount()) {
            p.setCapturedAmount(req.amount());
            p.setStatus(PaymentStatus.PARTIALLY_CAPTURED);
        } else {
            p.setCapturedAmount(p.getAmount());
            p.setStatus(PaymentStatus.CAPTURED);
        }
        repo.save(p);
        OutboxEvent outboxEvent = new OutboxEvent(p.getId(), p.getOrderId(), p.getStatus(), p.getCapturedAmount());
        outbox.save(outboxEvent);
        return new PaymentResponse(p.getId(), p.getStatus());
    }
}