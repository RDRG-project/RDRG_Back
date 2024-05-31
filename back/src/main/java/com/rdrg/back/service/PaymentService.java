package com.rdrg.back.service;

import org.springframework.http.ResponseEntity;

import com.rdrg.back.dto.request.payment.PostPaymentRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentDetailListResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentListResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentResponseDto;
import com.rdrg.back.dto.response.payment.PostPaymentResponseDto;

public interface PaymentService {
    ResponseEntity<? super PostPaymentResponseDto> postPayment(PostPaymentRequestDto dto, String userId);
    ResponseEntity<? super GetPaymentResponseDto> getPayment(String userId);
    ResponseEntity<? super GetPaymentListResponseDto> getPaymentList(String rentUserId);
    ResponseEntity<? super GetPaymentDetailListResponseDto> getPaymentDetailList(String rentUserId, int rentNumber);
    ResponseEntity<ResponseDto> deletePayment(int rentNumber);
}
