package com.rdrg.back.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    @NotBlank
    @Pattern(regexp="^[a-zA-Z0-9]{4,12}$")
    private String userId;
    @NotNull
    @Pattern(regexp="^[a-zA-Z0-9]*@([-.]?[a-zA-Z0-9])*\\.[a-zA-Z]{2,4}$")
    private String  userEmail;
    @NotNull
    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[0-9]).{8,13}$")
    private String userPassword;
    @NotBlank
    private String authNumber;
}
