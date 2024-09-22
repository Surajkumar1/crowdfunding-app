package com.example.demo.transformer.entity;


import com.example.demo.constants.CampaignMessage;
import com.example.demo.dto.client.response.GetCampaignResponse;
import com.example.demo.dto.service.request.CreateCampaignRequest;
import com.example.demo.dto.service.request.UpdateCampaignRequest;
import com.example.demo.entities.campaign.Campaign;
import com.example.demo.entities.campaign.Donation;
import com.example.demo.enums.CampaignStatus;
import com.example.demo.enums.DonationStatus;
import com.example.demo.utils.DateTimeParser;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CampaignEntityTransformer {

    public static Campaign transform(CreateCampaignRequest request){
        return Campaign.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(DateTimeParser.parseDate(request.getStartDate()))
                .endDate(DateTimeParser.parseDate(request.getEndDate()))
                .goalAmount(request.getGoalAmount())
                .status(CampaignStatus.ACTIVE)
                .donationAmount(BigDecimal.valueOf(0)).build();
    }

    public static void transform(Campaign campaign, UpdateCampaignRequest request){
        if(request.getTitle() != null) {
            campaign.setTitle(request.getTitle());
        }
        if(request.getDescription() != null) {
            campaign.setDescription(request.getDescription());
        }
        if(request.getStartDate() != null) {
            campaign.setStartDate(DateTimeParser.parseDate(request.getStartDate()));
        }
        if(request.getEndDate() != null) {
            campaign.setEndDate(DateTimeParser.parseDate(request.getEndDate()));
        }
        if(request.getGoalAmount() != null) {
            campaign.setGoalAmount(request.getGoalAmount());
        }
    }

    public static GetCampaignResponse transform(Campaign campaign){
        /*
        Double donations = 0.0;
        for(Donation donation: campaign.getDonationList()){
            if(donation.getStatus() == DonationStatus.SUCCESS){
                donations += donation.getAmount();
            }
        }
         */
        String message = "";
        if(campaign.getIsEffective()){
            message = CampaignMessage.CAMPAIGN_LIVE;
        } else if (campaign.getStartDate().toLocalDate().isAfter(LocalDate.now())){
            message = CampaignMessage.CAMPAIGN_NOT_LIVE_YET;
        } else {
            message = CampaignMessage.CAMPAIGN_ENDED;
        }
        return GetCampaignResponse.builder()
                .id(campaign.getId())
                .title(campaign.getTitle())
                .description(campaign.getDescription())
                .startDate(campaign.getStartDate())
                .endDate(campaign.getEndDate())
                .goalAmount(campaign.getGoalAmount())
                .status(campaign.getStatus())
                .userId(campaign.getUserId())
                .isEffective(campaign.getIsEffective())
                .message(message)
                .donations(campaign.getDonationAmount().doubleValue()).build();
    }

}
