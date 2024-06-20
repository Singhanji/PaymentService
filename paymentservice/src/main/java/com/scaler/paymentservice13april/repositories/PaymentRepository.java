package com.scaler.paymentservice13april.repositories;

import com.scaler.paymentservice13april.models.Payment;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment save(Payment payment);
//    Payment create(Payment payment);

    Payment findByPaymentGatewayReferenceId(String paymentGatewayReferenceId);
}
