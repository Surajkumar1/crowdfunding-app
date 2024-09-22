package com.example.demo.dto.client.request;

import com.example.demo.enums.PaymentStatus;
import lombok.Data;

@Data
public class UpdateDonationRequest {
    Long donationId;
    PaymentStatus status;
}
