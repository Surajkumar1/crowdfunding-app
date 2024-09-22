package com.example.demo.dto.client.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDonationResponse {
    Long donationId;
    Long paymentId;
}
