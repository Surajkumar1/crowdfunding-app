package com.example.demo.dto.client.response;

import com.example.demo.entities.campaign.Donation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDonationResponse {
    Donation data;
}
