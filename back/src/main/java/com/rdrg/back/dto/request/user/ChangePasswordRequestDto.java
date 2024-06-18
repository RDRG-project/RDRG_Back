package com.rdrg.back.dto.request.user;

import com.rdrg.back.common.util.PattenUtil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequestDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String userPassword;
    @NotBlank
    @Pattern(regexp = PattenUtil.PW_PATTERN)
    private String newUserPassword;
}
