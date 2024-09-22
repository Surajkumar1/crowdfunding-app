package com.example.demo.resources;

import com.example.demo.auth.CustomUserDetails;
import com.example.demo.dto.client.request.CreateDonationResponse;
import com.example.demo.dto.client.request.DonationRequest;
import com.example.demo.dto.client.request.UpdateDonationRequest;
import com.example.demo.dto.client.response.CreatePaymentResponse;
import com.example.demo.dto.client.response.PaymentApiResponse;
import com.example.demo.entities.campaign.Donation;
import com.example.demo.exceptions.CustomException;
import com.example.demo.exceptions.enums.ErrorCode;
import com.example.demo.services.DonationService;
import com.example.demo.transformer.service.CampaignServiceTransformer;
import com.example.demo.transformer.service.DonationServiceTransformer;
import com.example.demo.utils.UserContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.demo.constants.ResponseMessage.REQUEST_SUCCESS;
import static com.example.demo.exceptions.enums.ErrorCode.REQUEST_FAILED;

@RestController
@RequestMapping("/api/v1/donation")
public class DonationResource extends BaseController{

    @Autowired
    private DonationService donationService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    public ResponseEntity getDonationById(@PathVariable Long id) {
        Optional<Donation> response =  donationService.getDonationById(id);
        if(response.isPresent()){
            return successResponse(response.get(), REQUEST_SUCCESS);
        } else {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    @GetMapping("/my/donations")
    public ResponseEntity getDonationByUserId(@RequestParam(name = "page_size", defaultValue =  "10") Integer pageSize,
                                              @RequestParam(name = "page_num", defaultValue = "0") Integer pageNum) {
        Optional<CustomUserDetails> userDetails = UserContextUtility.fetchUserDetails();
        com.example.demo.dto.service.request.GetDonationRequest serviceRequest
                = DonationServiceTransformer.transform(userDetails.get().getUserId(), pageNum, pageSize);
        List<Donation> response =  donationService.getDonationByUserId(serviceRequest);
        return successResponse(response, REQUEST_SUCCESS);
    }

    @PostMapping("{campaign_id}/donate")
    public ResponseEntity createDonation(@PathVariable("campaign_id") Long campaignId,
                                         @RequestBody DonationRequest request, @RequestHeader Map<String, String> headers) {
        com.example.demo.dto.service.request.DonationRequest serviceRequest = DonationServiceTransformer.transform(request, campaignId);
        CreateDonationResponse response = donationService.makeDonation(serviceRequest, headers);
        return successResponse(response, REQUEST_SUCCESS);
    }

    @PostMapping("/update")
    public ResponseEntity updateDonation(@RequestBody UpdateDonationRequest request) {
        com.example.demo.dto.service.request.UpdateDonationRequest serviceRequest = DonationServiceTransformer.transform(request);
        Donation donation = donationService.updateDonation(serviceRequest);
        return successResponse(donation, REQUEST_SUCCESS);
    }

}