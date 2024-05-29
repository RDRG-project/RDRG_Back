package com.rdrg.back.dto.response.payment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rdrg.back.common.object.RentDetailList;
import com.rdrg.back.dto.response.ResponseCode;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.ResponseMessage;
import com.rdrg.back.entity.DeviceEntity;
import com.rdrg.back.entity.DeviceRentStatusEntity;

import lombok.Getter;

@Getter
public class GetPaymentDetailListResponseDto extends ResponseDto {
    private Integer rentNumber;
    private List<RentDetailList> rentDetailList;
    private String rentDatetime;
    private String rentReturnDatetime;
    private boolean rentStatus;
    private Integer rentTotalPrice;

    private GetPaymentDetailListResponseDto(List<DeviceEntity> rentDetailList, DeviceRentStatusEntity deviceRentStatusEntity) throws Exception {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

        this.rentNumber = deviceRentStatusEntity.getRentNumber();
        this.rentDetailList = RentDetailList.getNamePriceList(rentDetailList);
        this.rentDatetime = deviceRentStatusEntity.getRentDatetime();
        this.rentReturnDatetime = deviceRentStatusEntity.getRentReturnDatetime();
        this.rentStatus = deviceRentStatusEntity.isRentStatus();
    }

    public static ResponseEntity<GetPaymentDetailListResponseDto> success(DeviceRentStatusEntity deviceRentStatusEntity, List<DeviceEntity> deviceEntities) throws Exception {
        GetPaymentDetailListResponseDto responseBody = new GetPaymentDetailListResponseDto(deviceEntities, deviceRentStatusEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}
