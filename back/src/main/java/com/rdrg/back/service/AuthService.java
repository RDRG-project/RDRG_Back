package com.rdrg.back.service;

import org.springframework.http.ResponseEntity;

import com.rdrg.back.dto.request.auth.EmailAuthCheckRequestDto;
import com.rdrg.back.dto.request.auth.EmailAuthRequestDto;
import com.rdrg.back.dto.request.auth.IdCheckRequestDto;
import com.rdrg.back.dto.request.auth.SignInRequestDto;
import com.rdrg.back.dto.request.auth.SignUpRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.auth.SignInResponseDto;

public interface AuthService {
    ResponseEntity<? super SignInResponseDto> signIn (SignInRequestDto dto);
    ResponseEntity<ResponseDto> idCheck (IdCheckRequestDto dto);
    ResponseEntity<ResponseDto> emailAuth (EmailAuthRequestDto dto);
    ResponseEntity<ResponseDto> emailAuthCheck (EmailAuthCheckRequestDto dto);
    ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto);
}
