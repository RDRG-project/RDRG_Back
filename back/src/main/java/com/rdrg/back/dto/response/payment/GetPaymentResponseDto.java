package com.rdrg.back.dto.response.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rdrg.back.dto.response.ResponseCode;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.ResponseMessage;
import com.rdrg.back.entity.DeviceRentStatusEntity;

import lombok.Getter;

@Getter
public class GetPaymentResponseDto extends ResponseDto {
    private String userId;
    private String rentalPeriod;
    private String rentPlace;
    private Integer rentTotalPrice;

    private GetPaymentResponseDto(DeviceRentStatusEntity deviceRentStatusEntity) throws Exception {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        String totalRentalPeriod = deviceRentStatusEntity.getRentDatetime() + "~" + deviceRentStatusEntity.getRentReturnDatetime();

        this.userId = deviceRentStatusEntity.getRentUserId();
        this.rentalPeriod = totalRentalPeriod;
        this.rentPlace = deviceRentStatusEntity.getRentPlace();
        this.rentTotalPrice = deviceRentStatusEntity.getRentTotalPrice();
    }

    public static ResponseEntity<GetPaymentResponseDto> success(DeviceRentStatusEntity deviceRentStatusEntity) throws Exception {
        GetPaymentResponseDto responseBody = new GetPaymentResponseDto(deviceRentStatusEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
