package com.example.demo.dto.client.request;

import lombok.Data;

@Data
public class UpdatePaymentRequest {
    Long paymentId;
    String token;
    String status;
}
