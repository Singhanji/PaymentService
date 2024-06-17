package com.scaler.paymentservice13april.configs;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfig {
    @Value(value = "${razorpay.key_id}")
    private String razorpayKeyId;
    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;


    @Bean
    public RazorpayClient createRazorpayClient() throws RazorpayException {
        return new RazorpayClient(
                razorpayKeyId,
                razorpayKeySecret
        );
//    @Bean
//    public RazorpayClient createRazorpayClient() throws RazorpayException{
//        return new RazorpayClient(
//                System.getenv("RAZORPAY_KEY_ID"),
//                "RAZORPAY_KEY_SECRET"
//        );
    }
}
