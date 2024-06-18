package com.rdrg.back.dto.request.auth;

import com.rdrg.back.common.util.PattenUtil;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class EmailAuthRequestDto {
    @NotNull
    @Pattern(regexp=PattenUtil.EMAIL_PATTERN)
    private String userEmail;
}
