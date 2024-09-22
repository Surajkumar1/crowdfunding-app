package com.example.demo.resources;

import com.example.demo.auth.CustomUserDetails;
import com.example.demo.dto.client.request.CreateCampaignRequest;
import com.example.demo.dto.client.request.UpdateCampaignRequest;
import com.example.demo.dto.client.response.CreateCampaignResponse;
import com.example.demo.dto.client.response.GetCampaignResponse;
import com.example.demo.dto.client.response.UpdateCampaignResponse;
import com.example.demo.entities.campaign.Campaign;
import com.example.demo.enums.CampaignStatus;
import com.example.demo.exceptions.CustomException;
import com.example.demo.services.CampaignService;
import com.example.demo.services.DonationService;
import com.example.demo.transformer.service.CampaignServiceTransformer;
import com.example.demo.utils.UserContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.demo.constants.ResponseMessage.*;
import static com.example.demo.exceptions.enums.ErrorCode.*;

@RestController
@RequestMapping("/api/v1/innovator/campaign")
public class CampaignManagementResource extends BaseController{

    @Autowired
    private CampaignService campaignService;


    @Autowired
    private DonationService donationService;


    @PostMapping("/create")
    public ResponseEntity createCampaign(@RequestBody CreateCampaignRequest request) {
        com.example.demo.dto.service.request.CreateCampaignRequest serviceRequest = CampaignServiceTransformer.transform(request);
        Campaign campaign = campaignService.createCampaign(serviceRequest);
        CreateCampaignResponse response = CreateCampaignResponse.builder().campaignId(campaign.getId()).build();
        return successResponse(response, CAMPAIGN_CREATION_SUCCESS);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCampaign(@PathVariable Long id, @RequestBody UpdateCampaignRequest request) {
        com.example.demo.dto.service.request.UpdateCampaignRequest serviceRequest = CampaignServiceTransformer.transform(request);
        Campaign campaign = campaignService.updateCampaign(id, serviceRequest);
        UpdateCampaignResponse response = UpdateCampaignResponse.builder().campaignId(campaign.getId()).build();
        return successResponse(response, CAMPAIGN_UPDATED_SUCCESS);
    }

}