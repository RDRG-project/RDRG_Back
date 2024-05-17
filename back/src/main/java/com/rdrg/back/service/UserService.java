package com.rdrg.back.service;

import org.springframework.http.ResponseEntity;

import com.rdrg.back.dto.request.user.ChangePasswordRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.user.GetSignInUserResponseDto;
import com.rdrg.back.dto.response.user.GetUserInfoResponseDto;

public interface UserService {
    
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser (String userId);
    ResponseEntity<ResponseDto> changePassword (ChangePasswordRequestDto dto);
    ResponseEntity<? super GetUserInfoResponseDto>  getUserInfo (String userId);
    
}
