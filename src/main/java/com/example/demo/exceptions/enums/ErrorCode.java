package com.example.demo.exceptions.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_REGISTRATION_FAILED("User registered failed", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTS("Username already exists. Please try other user names", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("User not found", HttpStatus.BAD_REQUEST),
    INCORRECT_PASSWORD("Incorrect password. Please try again", HttpStatus.BAD_REQUEST),
    INCORRECT_USER("Username is incorrect", HttpStatus.BAD_REQUEST),
    USER_LOGIN_FAILED("User login failed", HttpStatus.BAD_REQUEST),

    CAMPAIGN_NOT_FOUND("Campaign not found", HttpStatus.BAD_REQUEST),
    CAMPAIGN_CREATION_FAILED("Campaign creation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    CAMPAIGN_UPDATE_FAILED("Campaign Update failed", HttpStatus.INTERNAL_SERVER_ERROR),

    RESOURCE_NOT_FOUND("Resource not found", HttpStatus.BAD_REQUEST),

    REQUEST_FAILED("Request failed", HttpStatus.INTERNAL_SERVER_ERROR),

    CAMPAIGN_ALREADY_COMPLETED("Campaign already completed", HttpStatus.BAD_REQUEST),

    CAMPAIGN_NOT_ACTIVE("Campaign is not active", HttpStatus.BAD_REQUEST),

    CAMPAIGN_ES_INGEST_FAILED("Failed to ingest campaign into ES", HttpStatus.INTERNAL_SERVER_ERROR),

    CAMPAIGN_INVALID_END_DATE("Campaign end date should not be before today", HttpStatus.BAD_REQUEST),

    CAMPAIGN_INVALID_START_DATE("Campaign start date should be today or later", HttpStatus.BAD_REQUEST),

    CAMPAIGN_INVALID_GOAL_AMOUNT("Campaign goal amount should be greater than existing donations", HttpStatus.BAD_REQUEST);

    String errorMessage;
    HttpStatus errorCode;

    ErrorCode(String errorMessage, HttpStatus errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

}
