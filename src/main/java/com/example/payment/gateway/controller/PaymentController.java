package com.example.payment.gateway.controller;

import com.example.payment.gateway.dto.CaptureRequest;
import com.example.payment.gateway.dto.PaymentRequest;
import com.example.payment.gateway.dto.PaymentResponse;
import com.example.payment.gateway.service.IdempotencyService;
import com.example.payment.gateway.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final IdempotencyService idempotencyService;


    @PostMapping
    public ResponseEntity<PaymentResponse> create(@RequestHeader("Idempotency-Key") String key,
                                                  @RequestBody PaymentRequest req) {
        if (idempotencyService.isProcessed(key)) {
            return ResponseEntity.ok((PaymentResponse) idempotencyService.getResult(key));
        }
        PaymentResponse resp = paymentService.create(req);
        idempotencyService.register(key, resp);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/{id}/capture")
    public ResponseEntity<PaymentResponse> capture(
            @RequestHeader("Idempotency-Key") String key,
            @PathVariable Long id,
            @RequestBody CaptureRequest req) {
        String lockKey = key + id;
        if (idempotencyService.isProcessed(lockKey)) {
            return ResponseEntity.ok((PaymentResponse) idempotencyService.getResult(lockKey));
        }
        PaymentResponse resp = paymentService.capture(id, req);
        idempotencyService.register(lockKey, resp);
        return ResponseEntity.ok(resp);
    }
}
