package com.scaler.paymentservice13april.configs;

import com.stripe.Stripe;
import com.stripe.StripeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {


    @Value(value = "${stripe.secret_key}")
    private String stripeKeySecret;

    @Bean
    public StripeClient stripeClient(){
        Stripe.apiKey = stripeKeySecret;
        return new StripeClient(stripeKeySecret);
    }

//    @Bean
//    public StripeClient stripeClient(){
//        Stripe.apiKey=stripeKeySecret;
//        return new StripeClient(
//                System.getenv("STRIPE_KEY_SECRET"));
//    }
}
