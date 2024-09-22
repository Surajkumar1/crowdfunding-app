package com.example.demo.dto.client.request;

import lombok.Data;

@Data
public class UpdateCampaignRequest {
    String title;
    String description;
    Double goalAmount;
    String startDate;
    String endDate;
}
