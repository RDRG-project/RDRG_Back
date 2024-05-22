package com.rdrg.back.dto.request.payment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostPaymentRequestDto {
    
    @NotBlank
    private Integer rentNumber;
    @NotBlank
    private String rentUserId;
    @NotBlank
    private String rentSerialNumber;
    @NotBlank
    private String name;
    @NotBlank
    private String type;
    @NotBlank
    private String rentPlace;
    @NotBlank
    private String rentReturnPlace;
    @NotBlank
    private String rentDatetime;
    @NotBlank
    private String rentReturnDatetime;
    @NotBlank
    private Integer rentTotalPrice;

}
