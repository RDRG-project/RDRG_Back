package com.rdrg.back.dto.response.payment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rdrg.back.common.object.AdminRentItem;
import com.rdrg.back.dto.response.ResponseCode;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.ResponseMessage;

import lombok.Getter;

@Getter
public class GetSearchAdminPaymentListResponseDto extends ResponseDto {
    private List<AdminRentItem> adminRentList;

    private GetSearchAdminPaymentListResponseDto (List<AdminRentItem> adminRentList) throws Exception {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.adminRentList = adminRentList;
    }

    public static ResponseEntity<GetSearchAdminPaymentListResponseDto> success(List<AdminRentItem> adminRentList) throws Exception {
        GetSearchAdminPaymentListResponseDto responseBody = new GetSearchAdminPaymentListResponseDto(adminRentList);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
