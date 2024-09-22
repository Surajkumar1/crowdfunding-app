package com.example.demo.transformer.entity;


import com.example.demo.dto.service.request.CreatePaymentRequest;
import com.example.demo.dto.service.request.UpdatePaymentRequest;
import com.example.demo.entities.payment.Payment;
import com.example.demo.enums.PaymentStatus;

public class PaymentEntityTransformer {

    public static Payment transform(CreatePaymentRequest request){
        return Payment.builder()
                .donationId(request.getDonationId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .status(PaymentStatus.IN_PROGRESS).build();
    }

    public static Payment transform(Payment payment, UpdatePaymentRequest request){
        payment.setToken(request.getToken());
        PaymentStatus status = switch (request.getStatus()) {
            case "Y" -> PaymentStatus.SUCCESS;
            case "N" -> PaymentStatus.FAILURE;
            default -> payment.getStatus();
        };
        payment.setStatus(status);
        return payment;
    }


}
