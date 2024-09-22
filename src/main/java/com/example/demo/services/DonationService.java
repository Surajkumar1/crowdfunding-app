package com.example.demo.services;

import com.example.demo.dto.client.request.CreateDonationResponse;
import com.example.demo.dto.client.request.CreatePaymentRequest;
import com.example.demo.dto.client.response.PaymentApiResponse;
import com.example.demo.dto.service.request.DonationRequest;
import com.example.demo.dto.service.request.GetDonationRequest;
import com.example.demo.dto.service.request.UpdateDonationRequest;
import com.example.demo.entities.campaign.Campaign;
import com.example.demo.entities.campaign.Donation;
import com.example.demo.enums.CampaignStatus;
import com.example.demo.enums.DonationStatus;
import com.example.demo.exceptions.CustomException;
import com.example.demo.exceptions.enums.ErrorCode;
import com.example.demo.repository.campaign.CampaignRepository;
import com.example.demo.repository.campaign.DonationRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.transformer.entity.DonationEntityTransformer;
import com.example.demo.utils.UserContextUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.exceptions.enums.ErrorCode.CAMPAIGN_NOT_FOUND;
import static com.example.demo.exceptions.enums.ErrorCode.USER_NOT_FOUND;

@Service
@Slf4j
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${rest.url.create.payment}")
    private String createPaymentUrl;

    @Autowired
    private RestTemplate restTemplate;

    public Double getDonationMoney(Campaign campaign) {
        return donationRepository.getDonatedMoney(DonationStatus.SUCCESS, campaign);
    }

    public Optional<Donation> getDonationById(Long id) {
        return donationRepository.findById(id);
    }


    public List<Donation> getDonationByUserId(GetDonationRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.by("updatedAt").descending());
        return donationRepository.findByUserId(request.userId, pageable).get().collect(Collectors.toList());
    }


    public CreateDonationResponse makeDonation(DonationRequest request, Map<String, String> headers) {
        Campaign campaign = campaignRepository.findById(request.getCampaignId())
                .orElseThrow(() -> new CustomException(CAMPAIGN_NOT_FOUND));
        validate(campaign);
        Donation donation = DonationEntityTransformer.transform(request);
        Long userId = UserContextUtility.fetchUserId();
        donation.setUserId(userId);
        donation.setCampaign(campaign);
        donation = donationRepository.save(donation);
        PaymentApiResponse paymentResponse = makePaymentEntry(donation, headers);
        return CreateDonationResponse.builder()
                .donationId(donation.getId())
                .paymentId(paymentResponse.getData().getPaymentId()).build();
    }

    private void validate(Campaign campaign){
        if(!campaign.getIsEffective()) {
            throw new CustomException(ErrorCode.CAMPAIGN_NOT_ACTIVE);
        }
    }

    private PaymentApiResponse makePaymentEntry(Donation donation, Map<String, String> headers){
        CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();
        createPaymentRequest.setDonationId(donation.getId());
        createPaymentRequest.setUserId(donation.getUserId());
        createPaymentRequest.setAmount(donation.getAmount());
        HttpHeaders httpHeaders = new HttpHeaders();
        if(headers.containsKey("authorization")){
            httpHeaders.set("authorization", headers.get("authorization"));
        }
        HttpEntity<CreatePaymentRequest> httpEntity = new HttpEntity<CreatePaymentRequest>(createPaymentRequest, httpHeaders);
        return restTemplate.postForObject(createPaymentUrl, httpEntity, PaymentApiResponse.class);
    }

    @Transactional(transactionManager = "campaignTransactionManager")
    public Donation updateDonation(UpdateDonationRequest request) {
        Optional<Donation> response = donationRepository.findById(request.getDonationId());
        if(response.isEmpty()){
            throw new CustomException(USER_NOT_FOUND);
        }
        Donation donation = DonationEntityTransformer.transform(response.get(), request);
        donation = donationRepository.save(donation);
        Campaign campaign = campaignRepository.findByIdWithLock(donation.getCampaign().getId()).get();
        campaign.setDonationAmount(campaign.getDonationAmount().add(BigDecimal.valueOf(donation.getAmount())));
        if(campaign.getDonationAmount().doubleValue() >= campaign.getGoalAmount()){
            campaign.setStatus(CampaignStatus.COMPLETED);
            // notify the innovator via notification service
        }
        return donation;
    }


}