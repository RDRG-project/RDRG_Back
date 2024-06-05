package com.rdrg.back.dto.response.payment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rdrg.back.common.object.RentDetailList;
import com.rdrg.back.dto.response.ResponseCode;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.ResponseMessage;
import com.rdrg.back.entity.DeviceRentStatusEntity;
import com.rdrg.back.entity.RentDetailEntity;

import lombok.Getter;

@Getter
public class GetPaymentDetailListResponseDto extends ResponseDto {
    private Integer rentNumber;
    private List<RentDetailList> rentDetailList;
    private String rentDatetime;
    private String rentReturnDatetime;
    private String rentPlace;
    private String rentReturnPlace;
    private String rentStatus;
    private Integer rentTotalPrice;

    private GetPaymentDetailListResponseDto( List<RentDetailEntity> rentDetailEntities, DeviceRentStatusEntity deviceRentStatusEntity) throws Exception {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

        this.rentNumber = deviceRentStatusEntity.getRentNumber();
        this.rentDetailList = RentDetailList.getNamePriceList(rentDetailEntities);
        this.rentDatetime = deviceRentStatusEntity.getRentDatetime();
        this.rentReturnDatetime = deviceRentStatusEntity.getRentReturnDatetime();
        this.rentPlace = deviceRentStatusEntity.getRentPlace();
        this.rentReturnPlace = deviceRentStatusEntity.getRentReturnPlace();
        this.rentStatus = deviceRentStatusEntity.getRentStatus();
        this.rentTotalPrice = deviceRentStatusEntity.getRentTotalPrice();
    }

    public static ResponseEntity<GetPaymentDetailListResponseDto> success(DeviceRentStatusEntity deviceRentStatusEntity, List<RentDetailEntity> rentDetailEntities) throws Exception {
        GetPaymentDetailListResponseDto responseBody = new GetPaymentDetailListResponseDto(rentDetailEntities, deviceRentStatusEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}
