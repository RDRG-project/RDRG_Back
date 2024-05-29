package com.rdrg.back.service.implementation;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rdrg.back.common.object.RentDetailList;
import com.rdrg.back.common.object.RentItem;
import com.rdrg.back.dto.request.payment.PostPaymentRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentDetailListResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentListResponseDto;
import com.rdrg.back.dto.response.payment.GetPaymentResponseDto;
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
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final PaymentRepository paymentRepository;
    private final RentDetailRepository rentDetailRepository;
    
    @Override
    public ResponseEntity<ResponseDto> postPayment(PostPaymentRequestDto dto, String userId) {

        try {
            if ("대여중".equals(dto.isRentStatus())) return ResponseDto.notRentalDevice();
            
            boolean isExistUser = userRepository.existsByUserId(userId);
            if (!isExistUser) return ResponseDto.authenticationFailed();

            DeviceRentStatusEntity deviceRentStatusEntity = new DeviceRentStatusEntity(dto, userId);
            paymentRepository.save(deviceRentStatusEntity);

            List<String> rentSerialNumbers = dto.getRentSerialNumber();
            
            List<RentDetailEntity> rentDetailEntities = new ArrayList<>();
            Integer rentNumber =  deviceRentStatusEntity.getRentNumber();

            for (String rentSeralNumber: rentSerialNumbers) {
                RentDetailEntity rentDetailEntity = new RentDetailEntity(rentNumber, rentSeralNumber);
                rentDetailEntities.add(rentDetailEntity);
            }

            rentDetailRepository.saveAll(rentDetailEntities);

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
    public ResponseEntity<? super GetPaymentListResponseDto> getPaymentList(String rentUserId) {

        try {
            boolean isExistUser = userRepository.existsByUserId(rentUserId);
            if(!isExistUser) return ResponseDto.authenticationFailed();

            List<DeviceRentStatusEntity> deviceRentStatusEntities = paymentRepository.findByRentUserIdOrderByRentNumberDesc(rentUserId);

            List<RentItem> rentList = new ArrayList<>();

            for (DeviceRentStatusEntity deviceRentStatusEntity: deviceRentStatusEntities) {
                Integer rentNumber =  deviceRentStatusEntity.getRentNumber();
                List<DeviceEntity> deviceEntities = deviceRepository.findRentDevices(rentNumber);
                RentItem rentItem = new RentItem(deviceRentStatusEntity, deviceEntities);
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
            
            List<DeviceEntity> deviceEntities = deviceRepository.findRentDevices(rentNumber);

            return GetPaymentDetailListResponseDto.success(deviceRentStatusEntity, deviceEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

}
