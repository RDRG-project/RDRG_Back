package com.rdrg.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rdrg.back.dto.request.user.ChangePasswordRequestDto;
import com.rdrg.back.dto.response.ResponseDto;
import com.rdrg.back.dto.response.user.GetSignInUserResponseDto;
import com.rdrg.back.dto.response.user.GetUserInfoResponseDto;
import com.rdrg.back.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rdrg/user")
@RequiredArgsConstructor

public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/")
    public ResponseEntity <? super GetSignInUserResponseDto> getSignInUser(
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(userId);
        return response;
    }

    @GetMapping("/{userId}")
    public ResponseEntity <? super GetUserInfoResponseDto> getUserInfo (
        @PathVariable("userId") String userId
        ) {
        ResponseEntity<? super GetUserInfoResponseDto> response = userService.getUserInfo(userId);
        return response;
    }


    @PatchMapping("/changePw")
    public ResponseEntity<ResponseDto> changePassword (
        @RequestBody @Valid ChangePasswordRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = userService.changePassword(requestBody);
        return response;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDto> deleteUser (
        @PathVariable("userId") String userId
    ){
        ResponseEntity<ResponseDto> response = userService.deleteUser(userId);
        return response;
    }
}
