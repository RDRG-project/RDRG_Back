package com.rdrg.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rdrg.back.dto.request.device.PostDeviceRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.device.GetDeviceListResponseDto;
import com.rdrg.back.service.DeviceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rdrg/device")
@RequiredArgsConstructor

public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/")
    ResponseEntity<ResponseDto> PostDevice(
        @RequestBody @Valid PostDeviceRequestDto requestBody,
        @AuthenticationPrincipal String serialNumber
    ) {
        ResponseEntity<ResponseDto> response = deviceService.postDevice(requestBody, serialNumber);
        return response;
    }

    @GetMapping("/list")
    public ResponseEntity<? super GetDeviceListResponseDto> getDeviceList(
        @RequestParam("start") String rentDatetime,
        @RequestParam("end") String restReturnDatetime
    ) {
        ResponseEntity<? super GetDeviceListResponseDto> response = deviceService.getDeviceList(rentDatetime, restReturnDatetime);
        return response;
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<ResponseDto> deleteDevice(
        @PathVariable("serialNumber") String serialNumber
    ) {
        ResponseEntity<ResponseDto> response = deviceService.deleteDevice(serialNumber);
        return response;
    }
}
