package com.rdrg.back.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rdrg.back.dto.response.ResponseDto;

@RestControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<ResponseDto> validationExceptionHandler(Exception exception) {
        exception.printStackTrace();
        return ResponseDto.validationFailed();
    }
}
