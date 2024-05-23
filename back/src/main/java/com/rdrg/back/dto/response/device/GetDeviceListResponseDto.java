package com.rdrg.back.dto.response.device;

import lombok.Getter;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rdrg.back.common.object.DeviceListItem;
import com.rdrg.back.dto.response.ResponseCode;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.ResponseMessage;
import com.rdrg.back.entity.DeviceEntity;

@Getter
public class GetDeviceListResponseDto extends ResponseDto {
    private List<DeviceListItem> deviceList;
    
    private GetDeviceListResponseDto(List<DeviceEntity> deviceEntities) throws Exception {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.deviceList = DeviceListItem.getList(deviceEntities);
    }

    public static ResponseEntity<GetDeviceListResponseDto> success (List<DeviceEntity> deviceEntities) throws Exception {
        GetDeviceListResponseDto responseBody = new GetDeviceListResponseDto(deviceEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
