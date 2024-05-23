package com.rdrg.back.service.implementation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rdrg.back.dto.request.device.PostDeviceRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.device.GetDeviceListResponseDto;
import com.rdrg.back.entity.DeviceEntity;
import com.rdrg.back.repository.DeviceRepository;
import com.rdrg.back.service.DeviceService;

import lombok.RequiredArgsConstructor;
import java.util.List;


@Service
@RequiredArgsConstructor

public class DeviceServiceImplementation implements DeviceService {
    private final DeviceRepository deviceRepository;

    @Override
    public ResponseEntity<ResponseDto> postDevice(PostDeviceRequestDto dto, String serialNumber) {
        
        try {
            boolean isExistSerialNumber = deviceRepository.existsBySerialNumber(serialNumber);
            if (isExistSerialNumber) return ResponseDto.authenticationFailed();
            
            DeviceEntity deviceEntity = new DeviceEntity(dto, serialNumber);
            deviceRepository.save(deviceEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetDeviceListResponseDto> getDeviceList(String inputRentDatetime, String inputReturnDatetime) {
        
        try {
            List<DeviceEntity> deviceEntities = deviceRepository.findAvailableDevices(inputRentDatetime, inputReturnDatetime);
            return GetDeviceListResponseDto.success(deviceEntities);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<ResponseDto> deleteDevice(String serialNumber) {
        
        try {
            DeviceEntity deviceEntity = deviceRepository.findBySerialNumber(serialNumber);
            if (deviceEntity == null) ResponseDto.noExistBoard();

            deviceRepository.delete(deviceEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        } 
        return ResponseDto.success();
    }
}
