package com.scaler.paymentservice13april.paymentGateways;

import com.scaler.paymentservice13april.models.PaymentStatus;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentGateway implements PaymentGatewayInterface {

    @Autowired
    private StripeClient stripeClient;

    @Override
    public String createPaymentLink(Long amount, String userName, String userEmail, String userPhone, Long orderId) {
        CustomerCreateParams createParams = CustomerCreateParams.builder()
                .setName(userName)
                .setEmail(userEmail)
                .setPhone(userPhone)
                .build();
            try {
                Customer customer = stripeClient.customers().create(createParams);
            }catch (StripeException stripeException) {
                System.out.println(stripeException);
                System.out.println("Something went wrong while creating customer");
            }

        ProductCreateParams productParams = ProductCreateParams.builder()
                .setId(orderId.toString()).setName("Laptop").build();

        Product product = null;

        try {
            product = stripeClient.products().create(productParams);
        }catch (StripeException stripeException){
            System.out.println(stripeException);
            System.out.println("Something went wrong while creating product");
        }

        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setCurrency("INR")
                .setUnitAmount(amount)
                .setProduct(product.getId())
                .build();

        Price price = null;
        try {
            price = stripeClient.prices().create(priceParams);
        }catch (StripeException stripeException){
            System.out.println(stripeException);
            System.out.println("Something went wrong while creating price");
        }

        PaymentLink paymentLink = null;
        try {
            PaymentLinkCreateParams linkCreateParams = PaymentLinkCreateParams.builder()
                    .addLineItem(
                            PaymentLinkCreateParams.LineItem.builder()
                                    .setPrice(price.getId())
                                    .setQuantity(1L)
                                    .build()
                    )
                    .setAfterCompletion(
                            PaymentLinkCreateParams.AfterCompletion.builder()
                                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                    .setRedirect(
                                            PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                    .setUrl("https://scaler.com")
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();
            paymentLink = stripeClient.paymentLinks().create(linkCreateParams);
            } catch (StripeException stripeException) {
            System.out.println(stripeException);
                System.out.println("Something is WRONG  while creating payment link");
            }
        return paymentLink.getUrl();

    }
    
    @Override
    public PaymentStatus getPaymentStatus(String paymentId) {
        return null;
    }
}

