package com.example.demo.dto.client.response;

import com.example.demo.enums.CampaignStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class GetCampaignResponse {
    private Long id;
    private String title;
    private String description;
    private Double goalAmount;
    private Date startDate;
    private Date endDate;
    private CampaignStatus status;
    private Long userId;
    private double donations;
}
