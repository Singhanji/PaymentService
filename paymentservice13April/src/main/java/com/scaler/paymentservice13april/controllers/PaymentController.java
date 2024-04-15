package com.scaler.paymentservice13april.controllers;

import com.scaler.paymentservice13april.dtos.CreatePaymentLinkRequestDto;
import com.scaler.paymentservice13april.dtos.CreatePaymentLinkResponseDto;
import com.scaler.paymentservice13april.models.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.scaler.paymentservice13april.services.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private  PaymentService paymentService;

//    public PaymentController(PaymentService paymentService){
//        this.paymentService = paymentService;
//    }

    @PostMapping()
    public CreatePaymentLinkResponseDto createPaymentLink(@RequestBody CreatePaymentLinkRequestDto request){
        String redirectUrl = this.paymentService.createPaymentLink(request.getOrderId());

        CreatePaymentLinkResponseDto response = new CreatePaymentLinkResponseDto();
        response.setUrl(redirectUrl);
        return response;

    }
    @GetMapping("{id}")
    public PaymentStatus checkPaymentStatus(@PathVariable("id") String paymentId){
        return this.paymentService.getPaymentStatus(paymentId);
    }

//    public void handleWebhookEvent() {}
}


// user ---> createOrder() --> OrderService
// user ---> createPaymentLink() --> PaymentService
// user (Order Details Page)---> checkPaymentStatus()  ----> PaymentService
// PaymentGateway(which is a webhook) ---will be sending a request to---> PaymentService