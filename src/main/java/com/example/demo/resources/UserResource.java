package com.example.demo.resources;

import com.example.demo.auth.AuthService;
import com.example.demo.dto.client.request.UserRegisterRequest;
import com.example.demo.dto.client.request.UserSignInRequest;
import com.example.demo.dto.client.response.LoginResponse;
import com.example.demo.exceptions.CustomException;
import com.example.demo.services.UserService;
import com.example.demo.transformer.service.UserServiceTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.constants.ResponseMessage.USER_LOGIN_SUCCESS;
import static com.example.demo.constants.ResponseMessage.USER_REGISTRATION_SUCCESS;
import static com.example.demo.exceptions.enums.ErrorCode.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserResource extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody UserRegisterRequest request) {
        com.example.demo.dto.service.request.UserRegisterRequest serviceRequest = UserServiceTransformer.transform(request);
        userService.signUp(serviceRequest);
        return successResponse(request.getUserName(), USER_REGISTRATION_SUCCESS);
    }

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody UserSignInRequest request) {
        String token = authService.authenticate(request.getUserName(), request.getPassword());
        LoginResponse response = LoginResponse.builder().token(token).build();
        return successResponse(response, USER_LOGIN_SUCCESS);
    }

}