package com.scaler.paymentservice13april.paymentGateways;

import com.scaler.paymentservice13april.models.PaymentStatus;

public class StripePaymentGateway implements PaymentGatewayInterface {

    @Override
    public String createPaymentLink(Long amount, String userName, String userEmail, String userPhone, Long orderId) {
        
        return "";
    }

    @Override
    public PaymentStatus getPaymentStatus(String paymentId) {
        return null;
    }

}
