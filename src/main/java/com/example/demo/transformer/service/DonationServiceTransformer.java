package com.example.demo.transformer.service;

import com.example.demo.dto.service.request.*;
import com.example.demo.enums.DonationStatus;

public class DonationServiceTransformer {

    public static DonationRequest transform(com.example.demo.dto.client.request.DonationRequest request, Long campaignId){
        return DonationRequest.builder()
                .campaignId(campaignId)
                .amount(request.getAmount()).build();
    }

    public static UpdateDonationRequest transform(com.example.demo.dto.client.request.UpdateDonationRequest request){
        return UpdateDonationRequest.builder()
                .donationId(request.getDonationId())
                .status(DonationStatus.valueOf(request.getStatus().name())).build();
    }

    public static GetDonationRequest transform(Long userId, Integer pageNum, Integer pageSize){
        return GetDonationRequest.builder()
                .userId(userId)
                .pageNum(pageNum)
                .pageSize(pageSize).build();
    }

}
