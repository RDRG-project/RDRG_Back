package com.rdrg.back.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rdrg.back.dto.response.ResponseCode;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.ResponseMessage;
import com.rdrg.back.entity.UserEntity;

import lombok.Getter;

@Getter

public class GetSignInUserResponseDto extends ResponseDto {

    private String userId;
    private String userRole;

    public GetSignInUserResponseDto(UserEntity userEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
        this.userRole = userEntity.getUserRole();
    }

    public static ResponseEntity<GetSignInUserResponseDto> success (UserEntity userEntity) {
        GetSignInUserResponseDto responseBody = new GetSignInUserResponseDto(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
