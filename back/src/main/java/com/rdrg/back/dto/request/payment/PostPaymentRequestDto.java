package com.rdrg.back.dto.request.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostPaymentRequestDto {
    @NotBlank
    private String rentUserId;
    @NotBlank
    private String rentSerialNumber;
    @NotBlank
    private String rentPlace;
    @NotBlank
    private String rentReturnPlace;
    @NotBlank
    private String rentDatetime;
    @NotBlank
    private String rentReturnDatetime;
    @NotNull
    private Integer rentTotalPrice;
    private Integer rentStatus;
}
