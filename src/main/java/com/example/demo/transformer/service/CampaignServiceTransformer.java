package com.example.demo.transformer.service;

import com.example.demo.dto.service.request.*;

public class CampaignServiceTransformer {

    public static CreateCampaignRequest transform(com.example.demo.dto.client.request.CreateCampaignRequest request){
        return CreateCampaignRequest.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .goalAmount(request.getGoalAmount()).build();
    }

    public static UpdateCampaignRequest transform(com.example.demo.dto.client.request.UpdateCampaignRequest request){
        return UpdateCampaignRequest.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .goalAmount(request.getGoalAmount()).build();
    }

    public static GetCampaignRequest transform(Long userId, Integer pageNum, Integer pageSize){
        return GetCampaignRequest.builder()
                .userId(userId)
                .pageNum(pageNum)
                .pageSize(pageSize).build();
    }

}
