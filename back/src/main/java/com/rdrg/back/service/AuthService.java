package com.rdrg.back.service;

import org.springframework.http.ResponseEntity;

import com.rdrg.back.dto.request.auth.IdCheckRequestDto;
import com.rdrg.back.dto.request.auth.SignInRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.auth.SignInResponseDto;

public interface AuthService {
    ResponseEntity<? super SignInResponseDto> signIn (SignInRequestDto dto);
    ResponseEntity<ResponseDto> idCheck (IdCheckRequestDto dto);
}
