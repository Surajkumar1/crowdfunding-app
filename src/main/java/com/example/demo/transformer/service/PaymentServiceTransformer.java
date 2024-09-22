package com.example.demo.transformer.service;

import com.example.demo.dto.service.request.CreatePaymentRequest;
import com.example.demo.dto.service.request.UpdatePaymentRequest;

public class PaymentServiceTransformer {

    public static CreatePaymentRequest transform(com.example.demo.dto.client.request.CreatePaymentRequest request){
        return CreatePaymentRequest.builder()
                .donationId(request.getDonationId())
                .userId(request.getUserId())
                .amount(request.getAmount()).build();
    }

    public static UpdatePaymentRequest transform(com.example.demo.dto.client.request.UpdatePaymentRequest request){
        return UpdatePaymentRequest.builder()
                .paymentId(request.getPaymentId())
                .token(request.getToken())
                .status(request.getStatus()).build();
    }

}
