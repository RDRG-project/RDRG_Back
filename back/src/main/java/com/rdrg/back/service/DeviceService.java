package com.rdrg.back.service;

import org.springframework.http.ResponseEntity;

import com.rdrg.back.dto.request.device.PostDeviceRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.device.GetDeviceListResponseDto;

public interface DeviceService {
    ResponseEntity<? super GetDeviceListResponseDto> getAdminDeviceList();
    ResponseEntity<? super GetDeviceListResponseDto> getDeviceList(String inputRentDatetime, String inputReturnDatetime, String inputPlace);
    ResponseEntity<ResponseDto> postDevice(PostDeviceRequestDto dto, String serialNumber);
    ResponseEntity<ResponseDto> deleteDevice(String serialNumber);
}
