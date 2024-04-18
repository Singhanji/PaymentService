package com.scaler.paymentservice13april.services;

import com.scaler.paymentservice13april.models.Payment;
import com.scaler.paymentservice13april.models.PaymentGateway;
import com.scaler.paymentservice13april.models.PaymentStatus;
import com.scaler.paymentservice13april.paymentGateways.PaymentGatewayFactory;
import com.scaler.paymentservice13april.paymentGateways.PaymentGatewayInterface;
import com.scaler.paymentservice13april.paymentGateways.RazorPaymentGateway;
import com.scaler.paymentservice13april.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final RazorPaymentGateway razorPaymentGateway;
    private PaymentGatewayFactory paymentGatewayFactory;
    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentService(PaymentGatewayFactory paymentGatewayFactory, PaymentRepository paymentRepository, RazorPaymentGateway razorPaymentGateway){
        this.paymentGatewayFactory = paymentGatewayFactory;
        this.paymentRepository = paymentRepository;
        this.razorPaymentGateway = razorPaymentGateway;
    }
    public String createPaymentLink(Long orderId) {
        // I need to get the details of the order:
        //      - "amount" of the order

        // Order order = restTemplate.getForObject("", Order.class);
        // Long amount = order.getAmount();
        // String username = order.getUser.getName();
        // String userMobile = order.getUser.getPhoneNumber();
        // String userEmail = order.getUser.getEmail();

        // PaymentService now create an object of PaymentGateway
        Long amount = 1000L;
        String userName = "Anjana Singh";
        String userEmail = "abc@example.com";
        String userMobile = "+919876543210";

        PaymentGatewayInterface paymentGateway = paymentGatewayFactory.getBestPaymentGateway();
        
        String paymentLink;
        paymentLink = paymentGateway.createPaymentLink(
                amount,
                userName,
                userEmail,
                userMobile,
                orderId
        );
        Payment payment = new Payment();
        payment.setPaymentLink(paymentLink);
        payment.setOrderId(orderId);
        payment.setPaymentGateway(PaymentGateway.RAZORPAY);
        payment.setPaymentStatus(PaymentStatus.PENDING);  // Initially keeping it pending
        payment.setAmount(amount);

        paymentRepository.save(payment);
        return paymentLink;
    }

    public PaymentStatus getPaymentStatus(String paymentGatewayPaymentId){

        Payment payment = paymentRepository.findByPaymentGatewayReferenceId(paymentGatewayPaymentId);
        PaymentGatewayInterface paymentGateway = null;

        if(payment.getPaymentGateway().equals(PaymentGateway.RAZORPAY)){
//            PaymentGatewayInterface razorpayPaymentGateway = null;
            paymentGateway = razorPaymentGateway;
        }

        // updated the payment status in the database
        PaymentStatus paymentStatus = paymentGateway.getPaymentStatus(paymentGatewayPaymentId);
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
        return paymentStatus;
    }
}
