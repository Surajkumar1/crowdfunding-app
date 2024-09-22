package com.example.demo.resources;

import com.example.demo.auth.CustomUserDetails;
import com.example.demo.dto.client.response.GetCampaignResponse;
import com.example.demo.services.CampaignService;
import com.example.demo.transformer.service.CampaignServiceTransformer;
import com.example.demo.utils.UserContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.demo.constants.ResponseMessage.*;

@RestController
@RequestMapping("/api/v1/campaign")
public class CampaignResource extends BaseController{

    @Autowired
    private CampaignService campaignService;


    @GetMapping("/{id}")
    public ResponseEntity getCampaignById(@PathVariable Long id) {
        GetCampaignResponse response = campaignService.getCampaignById(id);
        return successResponse(response, REQUEST_SUCCESS);
    }

    @GetMapping("/browse")
    public ResponseEntity getCampaigns(@RequestParam(value = "userId", required = false) Long userId,
                                          @RequestParam(name = "page_size", defaultValue =  "10") Integer pageSize,
                                          @RequestParam(name = "page_num", defaultValue = "0") Integer pageNum) {
        com.example.demo.dto.service.request.GetCampaignRequest serviceRequest
                = CampaignServiceTransformer.transform(userId, pageNum, pageSize);
        List<GetCampaignResponse> response =  campaignService.getCampaigns(serviceRequest);
        return successResponse(response, REQUEST_SUCCESS);
    }

    @GetMapping("/my/projects")
    public ResponseEntity getUserCampaigns(@RequestParam(name = "page_size", defaultValue =  "10") Integer pageSize,
                                       @RequestParam(name = "page_num", defaultValue = "0") Integer pageNum) {
        Optional<CustomUserDetails> userDetails = UserContextUtility.fetchUserDetails();
        com.example.demo.dto.service.request.GetCampaignRequest serviceRequest
                = CampaignServiceTransformer.transform(userDetails.get().getUserId(), pageNum, pageSize);
        List<GetCampaignResponse> response =  campaignService.getCampaigns(serviceRequest);
        return successResponse(response, REQUEST_SUCCESS);
    }

}