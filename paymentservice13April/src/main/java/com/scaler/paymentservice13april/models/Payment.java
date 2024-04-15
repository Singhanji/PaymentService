package com.scaler.paymentservice13april.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment extends BaseModel{
    private Long amount;
    private PaymentStatus paymentStatus;
    private Long userId;
    private Long orderId;
    private String PaymentLink;
    private String paymentGatewayReferenceId;
    private PaymentGateway paymentGateway;


}
