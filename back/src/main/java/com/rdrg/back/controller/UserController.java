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
import com.rdrg.back.dto.response.user.DeleteUserResponseDto;
import com.rdrg.back.dto.response.user.GetSignInUserResponseDto;
import com.rdrg.back.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/user")

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
    

        

    ) {

        

    }


    @PatchMapping("/changePw")
    public ResponseEntity<ResponseDto> changePassword (
        @RequestBody @Valid ChangePasswordRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = userService.changePassword(requestBody);

        return response;
    }
}
