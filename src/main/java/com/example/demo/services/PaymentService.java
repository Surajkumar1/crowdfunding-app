package com.example.demo.services;

import com.example.demo.dto.client.request.UpdateDonationRequest;
import com.example.demo.dto.client.response.UpdateDonationResponse;
import com.example.demo.dto.service.request.CreatePaymentRequest;
import com.example.demo.dto.service.request.UpdatePaymentRequest;
import com.example.demo.entities.payment.Payment;
import com.example.demo.exceptions.CustomException;
import com.example.demo.repository.payment.PaymentRepository;
import com.example.demo.transformer.entity.PaymentEntityTransformer;
import com.example.demo.utils.UserContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.example.demo.exceptions.enums.ErrorCode.CAMPAIGN_NOT_FOUND;

@Service
public class PaymentService {

    @Value("${rest.url.update.donation}")
    private String updateDonationUrl;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment createPayment(CreatePaymentRequest request) {
        Payment payment = PaymentEntityTransformer.transform(request);
        Long userId = UserContextUtility.fetchUserId();
        payment.setUserId(userId);
        return paymentRepository.save(payment);
    }

    public void updatePayment(UpdatePaymentRequest request) {
        Optional<Payment> response = getPaymentById(request.getPaymentId());
        if(!response.isPresent()){
            throw new CustomException(CAMPAIGN_NOT_FOUND);
        }
        Payment payment = response.get();
        payment = PaymentEntityTransformer.transform(payment, request);
        paymentRepository.save(payment);
        updateDonation(payment);
    }

    private void updateDonation(Payment payment) {
        UpdateDonationRequest updateDonationRequest = new UpdateDonationRequest();
        updateDonationRequest.setDonationId(payment.getDonationId());
        updateDonationRequest.setStatus(payment.getStatus());
        restTemplate.postForObject(updateDonationUrl, updateDonationRequest, UpdateDonationResponse.class);
    }


}
