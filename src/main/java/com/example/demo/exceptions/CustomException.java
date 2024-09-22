package com.example.demo.exceptions;

import com.example.demo.exceptions.enums.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends RuntimeException {

    private String errorMessage;
    private HttpStatus errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorMessage = errorCode.getErrorMessage();
        this.errorCode = errorCode.getErrorCode();
    }

}