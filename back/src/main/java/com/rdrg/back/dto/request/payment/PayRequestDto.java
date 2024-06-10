package com.rdrg.back.dto.request.payment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayRequestDto {
    @NotBlank
    private String itemName;
    @NonNull
    private Integer quantity;
    @NonNull
    private Integer totalAmount;
    @NonNull
    private Integer vatAmount;

}
