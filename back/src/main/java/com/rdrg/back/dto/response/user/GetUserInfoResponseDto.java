package com.rdrg.back.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rdrg.back.dto.response.ResponseCode;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.ResponseMessage;
import com.rdrg.back.entity.UserEntity;

import lombok.Getter;
@Getter
public class GetUserInfoResponseDto extends ResponseDto {
    private String userId;
    private String userEmail;
    
    public GetUserInfoResponseDto(UserEntity userEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
        this.userEmail = userEntity.getUserEmail();
    }

    public static ResponseEntity<GetUserInfoResponseDto> success (UserEntity userEntity) {
        GetUserInfoResponseDto responseBody = new GetUserInfoResponseDto(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
