package com.example.payment.gateway.domain;

public enum PaymentStatus {
    INIT,          // заказ создан, средств ещё нет
    AUTHORIZED,    // деньги зарезервированы
    CAPTURED,      // средства списаны полностью
    PARTIALLY_CAPTURED, // частичное списание
    FAILED,        // ошибка при авторизации
    REFUNDED       // возврат
}
