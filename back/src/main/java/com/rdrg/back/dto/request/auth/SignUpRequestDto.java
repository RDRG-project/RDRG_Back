package com.rdrg.back.dto.request.auth;

import com.rdrg.back.common.util.PattenUtil;

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
    @Pattern(regexp=PattenUtil.ID_PATTERN)
    private String userId;
    @NotNull
    @Pattern(regexp=PattenUtil.EMAIL_PATTERN)
    private String  userEmail;
    @NotNull
    @Pattern(regexp=PattenUtil.PW_PATTERN)
    private String userPassword;
    @NotBlank
    private String authNumber;
}
