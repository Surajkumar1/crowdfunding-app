package com.example.demo.dto.service.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePaymentRequest {
    Long donationId;
    Long userId;
    Double amount;
}
