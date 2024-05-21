package com.rdrg.back.service;

import org.springframework.http.ResponseEntity;

import com.rdrg.back.dto.request.payment.PostPaymentRequestDto;
import com.rdrg.back.dto.response.ResponseDto;

public interface PaymentService {
    
    ResponseEntity<ResponseDto> postPayment(PostPaymentRequestDto dto, String userId);
}
