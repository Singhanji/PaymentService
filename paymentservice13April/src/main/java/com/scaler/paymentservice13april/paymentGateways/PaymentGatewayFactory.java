package com.scaler.paymentservice13april.paymentGateways;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayFactory {

    private final RazorPaymentGateway razorPaymentGateway;
    private final StripePaymentGateway stripePaymentGateway;


    public PaymentGatewayFactory( RazorPaymentGateway razorPaymentGateway, StripePaymentGateway stripePaymentGateway){
        this.razorPaymentGateway = razorPaymentGateway;
        this.stripePaymentGateway = stripePaymentGateway;
    }
    @Bean
    public PaymentGatewayInterface getBestPaymentGateway(){

//        int random = new Random().nextInt();
//        if (random % 2 == 0) return new RazorPaymentGateway();
//        return new StripePaymentGateway();
//        return razorPaymentGateway;
        return stripePaymentGateway;

    }

}
