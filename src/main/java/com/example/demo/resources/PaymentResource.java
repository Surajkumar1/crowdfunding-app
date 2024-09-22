package com.example.demo.resources;

import com.example.demo.dto.client.request.*;
import com.example.demo.dto.client.response.CreatePaymentResponse;
import com.example.demo.dto.client.response.UpdateDonationResponse;
import com.example.demo.entities.payment.Payment;
import com.example.demo.services.PaymentService;
import com.example.demo.transformer.service.PaymentServiceTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.example.demo.exceptions.enums.ErrorCode.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentResource extends BaseController{

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    public Optional<Payment> getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @PostMapping("/create")
    public ResponseEntity createPayment(@RequestBody CreatePaymentRequest request) {
        com.example.demo.dto.service.request.CreatePaymentRequest serviceRequest = PaymentServiceTransformer.transform(request);
        Payment payment = paymentService.createPayment(serviceRequest);
        CreatePaymentResponse response = CreatePaymentResponse.builder().paymentId(payment.getId()).build();
        return successResponse(response);
    }

    @PostMapping("/update")
    public ResponseEntity updatePaymentStatus(@RequestBody UpdatePaymentRequest request) {
        com.example.demo.dto.service.request.UpdatePaymentRequest serviceRequest = PaymentServiceTransformer.transform(request);
        paymentService.updatePayment(serviceRequest);
        return successResponse(request.getPaymentId());
    }

}