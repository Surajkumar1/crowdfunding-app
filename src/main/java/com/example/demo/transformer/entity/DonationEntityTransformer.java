package com.example.demo.transformer.entity;


import com.example.demo.dto.service.request.DonationRequest;
import com.example.demo.dto.service.request.UpdateDonationRequest;
import com.example.demo.entities.campaign.Donation;

import static com.example.demo.enums.DonationStatus.IN_PROGRESS;

public class DonationEntityTransformer {

    public static Donation transform(DonationRequest request){
        return Donation.builder()
                .amount(request.getAmount())
                .userId(request.getUserId())
                .status(IN_PROGRESS).build();
    }

    public static Donation transform(Donation donation, UpdateDonationRequest request){
        donation.setStatus(request.getStatus());
        return donation;
    }

}
