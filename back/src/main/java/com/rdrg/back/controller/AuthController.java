package com.rdrg.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rdrg.back.dto.request.auth.IdCheckRequestDto;
import com.rdrg.back.dto.request.auth.SignInRequestDto;
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
}
