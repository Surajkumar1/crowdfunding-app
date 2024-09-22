package com.example.demo.resources;

import com.example.demo.dto.client.response.ApiResponse;
import com.example.demo.exceptions.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<ApiResponse<T>> successResponse(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(true, message, data, HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    protected <T> ResponseEntity<ApiResponse<T>> successResponse(T data) {
        return successResponse(data, "Request was successful");
    }

    protected <T> ResponseEntity<ApiResponse<T>> errorResponse(ErrorCode errorCode) {
        ApiResponse<T> response = new ApiResponse<>(false, errorCode.getErrorMessage(), null, errorCode.getErrorCode().value());
        return new ResponseEntity<>(response, errorCode.getErrorCode());
    }

}