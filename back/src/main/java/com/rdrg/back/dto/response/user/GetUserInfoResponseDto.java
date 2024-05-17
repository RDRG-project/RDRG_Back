package com.rdrg.back.dto.response.user;

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
}
