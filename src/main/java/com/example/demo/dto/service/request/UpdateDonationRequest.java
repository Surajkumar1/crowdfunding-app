package com.example.demo.dto.service.request;

import com.example.demo.enums.DonationStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDonationRequest {
    Long donationId;
    DonationStatus status;
}
