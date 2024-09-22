package com.example.demo.dto.service.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCampaignRequest {

    public Long userId;
    public Integer pageNum;
    public Integer pageSize;

}
