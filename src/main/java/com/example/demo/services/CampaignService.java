package com.example.demo.services;

import com.example.demo.dto.client.response.GetCampaignResponse;
import com.example.demo.dto.service.request.CreateCampaignRequest;
import com.example.demo.dto.service.request.GetCampaignRequest;
import com.example.demo.dto.service.request.UpdateCampaignRequest;
import com.example.demo.entities.campaign.Campaign;
import com.example.demo.enums.CampaignStatus;
import com.example.demo.exceptions.CustomException;
import com.example.demo.exceptions.enums.ErrorCode;
import com.example.demo.repository.campaign.CampaignRepository;
import com.example.demo.transformer.entity.CampaignEntityTransformer;
import com.example.demo.utils.UserContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.exceptions.enums.ErrorCode.CAMPAIGN_NOT_FOUND;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

  //  @Autowired
   // private CampaignEsService campaignEsService;


    public List<GetCampaignResponse> getCampaigns(GetCampaignRequest request) {
        List<Campaign> campaigns;
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.by("updatedAt").descending());
        if(request.getUserId() != null){
            campaigns =  campaignRepository.findByUserId(request.userId, pageable).get().collect(Collectors.toList());
        } else {
            campaigns =  campaignRepository.findAll(pageable).get().collect(Collectors.toList());
        }
        return campaigns.stream().map(campaign -> CampaignEntityTransformer.transform(campaign)).collect(Collectors.toList());
    }

    public GetCampaignResponse getCampaignById(Long id) {
        Optional<Campaign> campaignResponse =  campaignRepository.findById(id);
        if(campaignResponse.isPresent()){
            return CampaignEntityTransformer.transform(campaignResponse.get());
        } else {
            throw new CustomException(CAMPAIGN_NOT_FOUND);
        }
    }


    public Campaign createCampaign(CreateCampaignRequest request) {
        Campaign campaign = CampaignEntityTransformer.transform(request);
        campaign.setUserId(UserContextUtility.fetchUserDetails().get().getUserId());
        campaign = campaignRepository.save(campaign);

        // send event campaign event to kakfa and consume to elastic search via consumer
        try {
          //  campaignEsService.saveCampaign(campaign);
        } catch (Exception e){
            throw new CustomException(ErrorCode.CAMPAIGN_ES_INGEST_FAILED);
        }

        return campaign;
    }

    public Campaign updateCampaign(Long id, UpdateCampaignRequest request) {
        Optional<Campaign> response = campaignRepository.findById(id);
        if (response.isPresent()) {
            Campaign campaign = response.get();
            validate(campaign, request);
            CampaignEntityTransformer.transform(campaign, request);
            return campaignRepository.save(campaign);
        } else {
            throw new CustomException(CAMPAIGN_NOT_FOUND);
        }
    }

    private void validate(Campaign campaign, UpdateCampaignRequest request){
        if(campaign.getStatus() == CampaignStatus.COMPLETED){
            throw new CustomException(ErrorCode.CAMPAIGN_ALREADY_COMPLETED);
        }
        if(campaign.getStartDate().compareTo(new Date(System.currentTimeMillis())) < 0){
            throw new CustomException(ErrorCode.CAMPAIGN_INVALID_START_DATE);
        }
        if(campaign.getEndDate().compareTo(new Date(System.currentTimeMillis())) < 0){
            throw new CustomException(ErrorCode.CAMPAIGN_INVALID_END_DATE);
        }
        if(request.getGoalAmount() < campaign.getDonationAmount().doubleValue()){
            throw new CustomException(ErrorCode.CAMPAIGN_INVALID_GOAL_AMOUNT);
        }
    }

    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }

}
