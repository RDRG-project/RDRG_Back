package com.rdrg.back.service.implementation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rdrg.back.dto.request.payment.PostPaymentRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentListResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentResponseDto;
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

            if (dto.getRentStatus().equals(0)) return ResponseDto.notFound();
            
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

    @Override
    public ResponseEntity<? super GetPaymentResponseDto> getPayment(String rentUserId) {

        try {

            boolean isExistUser = userRepository.existsByUserId(rentUserId);
            if(!isExistUser) return ResponseDto.authenticationFailed();

            DeviceRentStatusEntity reservations = paymentRepository.findTop1ByRentUserIdOrderByRentNumberDesc(rentUserId);
            if(reservations == null) return ResponseDto.notFound();

            return GetPaymentResponseDto.success(reservations);
            
        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }
    }

    @Override
    public ResponseEntity<? super GetPaymentListResponseDto> getPaymentList() {

        try {

            List<DeviceRentStatusEntity> deviceRentStatusEntities = paymentRepository.findByOrderByRentNumberDesc();
            return GetPaymentListResponseDto.success(deviceRentStatusEntities);
            
        } catch (Exception exception) {
            
            exception.printStackTrace();
            return ResponseDto.databaseError();

        }
    }
    
}
