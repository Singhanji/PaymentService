package com.scaler.paymentservice13april.paymentGateways;

import com.razorpay.RazorpayClient;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentGatewayFactory {

    private final RazorPaymentGateway razorPaymentGateway;

    public PaymentGatewayFactory( RazorPaymentGateway razorPaymentGateway){
        this.razorPaymentGateway = razorPaymentGateway;
    }
    public PaymentGatewayInterface getBestPaymentGateway(){

//        int random = new Random().nextInt();
//        if (random % 2 == 0) return new RazorPaymentGateway();
//        return new StripePaymentGateway();
        return razorPaymentGateway;
    }

}
