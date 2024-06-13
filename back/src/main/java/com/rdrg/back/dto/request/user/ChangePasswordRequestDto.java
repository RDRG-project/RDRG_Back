package com.rdrg.back.dto.request.user;

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
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~․!@#$%^&*()_\\-+=\\[\\]{}|\\\\;:‘“<>.,?/]).{8,19}$")
    private String newUserPassword;
}
