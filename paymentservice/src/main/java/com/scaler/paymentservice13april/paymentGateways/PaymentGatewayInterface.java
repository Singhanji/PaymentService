package com.scaler.paymentservice13april.paymentGateways;

import com.scaler.paymentservice13april.models.PaymentStatus;

public interface PaymentGatewayInterface {

    String createPaymentLink(
            Long amount,
            String userName,
            String userEmail,
            String userPhone,
            Long orderId
    );

    PaymentStatus getPaymentStatus(
            String paymentId
    );
}

//    PaymentStatus getPaymentStatus(Long paymentId);
//}
