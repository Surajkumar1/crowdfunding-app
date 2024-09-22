package com.example.demo.dto.client.request;

import lombok.Data;

@Data
public class CreatePaymentRequest {
    Long donationId;
    Long userId;
    Double amount;
}
