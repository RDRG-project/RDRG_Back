package com.rdrg.back.dto.request.user;

import jakarta.validation.constraints.NotBlank;
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
    private String newUserPassword;
    
}
