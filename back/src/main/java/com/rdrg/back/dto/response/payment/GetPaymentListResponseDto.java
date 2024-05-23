package com.rdrg.back.dto.response.payment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rdrg.back.common.object.RentItem;
import com.rdrg.back.dto.response.ResponseCode;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.ResponseMessage;
import com.rdrg.back.entity.DeviceRentStatusEntity;

import lombok.Getter;

@Getter
public class GetPaymentListResponseDto extends ResponseDto {
    private List<RentItem> rentList;

    private GetPaymentListResponseDto(List<DeviceRentStatusEntity> deviceRentStatusEntities) throws Exception {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.rentList = RentItem.getRentList(deviceRentStatusEntities, false);
    }

    public static ResponseEntity<GetPaymentListResponseDto> success(List<DeviceRentStatusEntity> deviceRentStatusEntities) throws Exception {
        GetPaymentListResponseDto responseBody = new GetPaymentListResponseDto(deviceRentStatusEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
