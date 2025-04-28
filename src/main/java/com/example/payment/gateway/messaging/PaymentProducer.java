package com.example.payment.gateway.messaging;

import com.example.payment.gateway.domain.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProducer {
    private static final String TOPIC = "payment.events.v1";
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public void send(PaymentEvent event) {
        kafkaTemplate.send(TOPIC, event.paymentId().toString(), event);
    }
}