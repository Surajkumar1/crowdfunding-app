package com.example.demo.resources;

import com.example.demo.dto.client.request.CreateCampaignRequest;
import com.example.demo.dto.client.request.UpdateCampaignRequest;
import com.example.demo.entities.campaign.Campaign;
import com.example.demo.services.CampaignService;
import com.example.demo.transformer.service.CampaignServiceTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.demo.constants.ResponseMessage.*;
import static com.example.demo.exceptions.enums.ErrorCode.*;

@RestController
@RequestMapping("/")
public class HealthResource extends BaseController{


    @GetMapping("/health")
    public ResponseEntity ping() {
        return successResponse("pong", REQUEST_SUCCESS);
    }

}