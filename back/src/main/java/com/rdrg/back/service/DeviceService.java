package com.rdrg.back.service;

import org.springframework.http.ResponseEntity;

import com.rdrg.back.dto.request.device.PostDeviceRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.device.GetDeviceListResponseDto;

public interface DeviceService {

    ResponseEntity<ResponseDto> postDevice(PostDeviceRequestDto dto, String serialNumber);

    ResponseEntity<? super GetDeviceListResponseDto> getDeviceList();

    ResponseEntity<ResponseDto> deleteDevice(String serialNumber);

}
