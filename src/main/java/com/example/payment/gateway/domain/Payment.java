package com.example.payment.gateway.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long amount;
    private Long capturedAmount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.INIT;
    @Version
    private Integer version;
}
