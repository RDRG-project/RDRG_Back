package com.rdrg.back.service.implementation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rdrg.back.dto.request.payment.PostPaymentRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.entity.DeviceRentStatusEntity;
import com.rdrg.back.repository.PaymentRepository;
import com.rdrg.back.repository.UserRepository;
import com.rdrg.back.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImplementation implements PaymentService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    
    @Override
    public ResponseEntity<ResponseDto> postPayment(PostPaymentRequestDto dto, String userId) {

        try {
            
            boolean isExistUser = userRepository.existsByUserId(userId);
            if (!isExistUser) return ResponseDto.authenticationFailed();

            DeviceRentStatusEntity deviceRentStatusEntity = new DeviceRentStatusEntity(dto, userId);
            paymentRepository.save(deviceRentStatusEntity);


        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }
        
        return ResponseDto.success();
        
    }
    
}
