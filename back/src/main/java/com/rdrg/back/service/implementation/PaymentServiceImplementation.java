package com.rdrg.back.service.implementation;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rdrg.back.common.object.KakaoReady;
import com.rdrg.back.common.object.RentItem;
import com.rdrg.back.common.util.KakaoPayUtil;
import com.rdrg.back.dto.request.payment.PatchRentStatusResponseDto;
import com.rdrg.back.dto.request.payment.PostPaymentRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentDetailListResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentListResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentResponseDto;
import com.rdrg.back.dto.response.payment.PostPaymentResponseDto;
import com.rdrg.back.entity.DeviceEntity;
import com.rdrg.back.entity.DeviceRentStatusEntity;
import com.rdrg.back.entity.RentDetailEntity;
import com.rdrg.back.entity.UserEntity;
import com.rdrg.back.repository.DeviceRepository;
import com.rdrg.back.repository.PaymentRepository;
import com.rdrg.back.repository.RentDetailRepository;
import com.rdrg.back.repository.UserRepository;
import com.rdrg.back.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImplementation implements PaymentService {
    private final KakaoPayUtil kakaoPayUtil;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final PaymentRepository paymentRepository;
    private final RentDetailRepository rentDetailRepository;
    
    @Override
    public ResponseEntity<? super PostPaymentResponseDto> postPayment(PostPaymentRequestDto dto, String userId) {

        KakaoReady kakaoReady = null;
        
        try {
            if ("대여중".equals(dto.getRentStatus())) return ResponseDto.notRentalDevice();
            
            boolean isExistUser = userRepository.existsByUserId(userId);
            if (!isExistUser) return ResponseDto.authenticationFailed();

            DeviceRentStatusEntity deviceRentStatusEntity = new DeviceRentStatusEntity(dto, userId);
            paymentRepository.save(deviceRentStatusEntity);

            List<String> rentSerialNumbers = dto.getRentSerialNumber();
            List<DeviceEntity> deviceEntities = deviceRepository.findAllById(rentSerialNumbers);
            
            List<RentDetailEntity> rentDetailEntities = new ArrayList<>();
            Integer rentNumber =  deviceRentStatusEntity.getRentNumber();

            for (DeviceEntity deviceEntity: deviceEntities) {
                RentDetailEntity rentDetailEntity = new RentDetailEntity(rentNumber, deviceEntity);
                rentDetailEntities.add(rentDetailEntity);
            }

            rentDetailRepository.saveAll(rentDetailEntities);
            
            kakaoReady = kakaoPayUtil.prepareKakaoPayment(dto, rentNumber);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostPaymentResponseDto.success(kakaoReady);
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
    public ResponseEntity<? super GetPaymentListResponseDto> getPaymentList(String rentUserId) {

        try {
            boolean isExistUser = userRepository.existsByUserId(rentUserId);
            if(!isExistUser) return ResponseDto.authenticationFailed();

            List<DeviceRentStatusEntity> deviceRentStatusEntities = paymentRepository.findByRentUserIdOrderByRentNumberDesc(rentUserId);

            List<RentItem> rentList = new ArrayList<>();

            for (DeviceRentStatusEntity deviceRentStatusEntity: deviceRentStatusEntities) {
                Integer rentNumber =  deviceRentStatusEntity.getRentNumber();
                List<RentDetailEntity> rentDetailEntities = rentDetailRepository.findByRentNumber(rentNumber);
                RentItem rentItem = new RentItem(deviceRentStatusEntity, rentDetailEntities);
                rentList.add(rentItem);
            }
            
            return GetPaymentListResponseDto.success(rentList);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super GetPaymentDetailListResponseDto> getPaymentDetailList(String rentUserId, int rentNumber) {
        try {
            UserEntity userEntity = userRepository.findByUserId(rentUserId);
            if (userEntity == null) return ResponseDto.noExistUserId();

            DeviceRentStatusEntity deviceRentStatusEntity = paymentRepository.findByRentNumber(rentNumber);
            
            List<RentDetailEntity> rentDetailEntities = rentDetailRepository.findByRentNumber(rentNumber);

            return GetPaymentDetailListResponseDto.success(deviceRentStatusEntity, rentDetailEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<ResponseDto> deletePayment(int rentNumber) {

        try {
            DeviceRentStatusEntity deviceRentStatusEntity = paymentRepository.findByRentNumber(rentNumber);
            if (deviceRentStatusEntity == null) ResponseDto.noExistRentDetail();

            paymentRepository.delete(deviceRentStatusEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> patchRentStatus(int rentNumber, PatchRentStatusResponseDto patchRentStatusResponseDto ) {

        try {
            DeviceRentStatusEntity deviceRentStatusEntity = paymentRepository.findByRentNumber(rentNumber);
            if (deviceRentStatusEntity == null) ResponseDto.noExistRentDetail();

            deviceRentStatusEntity.setRentStatus(patchRentStatusResponseDto.getRentStatus());
            paymentRepository.save(deviceRentStatusEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }
}
