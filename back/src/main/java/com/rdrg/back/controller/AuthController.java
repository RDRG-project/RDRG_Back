package com.rdrg.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rdrg.back.dto.request.auth.EmailAuthCheckRequestDto;
import com.rdrg.back.dto.request.auth.EmailAuthRequestDto;
import com.rdrg.back.dto.request.auth.IdCheckRequestDto;
import com.rdrg.back.dto.request.auth.SignInRequestDto;
import com.rdrg.back.dto.request.auth.SignUpRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.auth.SignInResponseDto;
import com.rdrg.back.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rdrg/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn (
        @RequestBody @Valid SignInRequestDto requestBody
    ) {
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
        return response;
        }
    
    @PostMapping("/id-check")
    public ResponseEntity<ResponseDto> idCheck (
        @RequestBody @Valid IdCheckRequestDto requestBody
    ) {
        ResponseEntity<ResponseDto> response = authService.idCheck(requestBody);
        return response;
        }

    @PostMapping("/email-auth")
    public ResponseEntity<ResponseDto> emailAuth (
        @RequestBody @Valid EmailAuthRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = authService.emailAuth(requestBody);
        return response;
    }

    @PostMapping("/email-auth-check")
    public ResponseEntity<ResponseDto> emailAuthCheck (
        @RequestBody @Valid EmailAuthCheckRequestDto requestBody
    ) {
        ResponseEntity<ResponseDto> response = authService.emailAuthCheck(requestBody);
        return response;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> signup (
        @RequestBody @Valid SignUpRequestDto requestBody
    ) {
        ResponseEntity<ResponseDto> response = authService.signUp(requestBody);
        return response;
    }
}
