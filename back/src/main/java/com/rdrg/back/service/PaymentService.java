package com.rdrg.back.service;

import org.springframework.http.ResponseEntity;
import java.util.List;

import com.rdrg.back.dto.request.payment.PostPaymentRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentListResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentResponseDto;

public interface PaymentService {
    ResponseEntity<ResponseDto> postPayment(PostPaymentRequestDto dto, String userId);
    ResponseEntity<? super GetPaymentResponseDto> getPayment(String userId);
    ResponseEntity<? super GetPaymentListResponseDto> getPaymentList();
}
