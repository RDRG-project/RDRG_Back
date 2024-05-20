package com.rdrg.back.dto.request.device;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class DeleteDeviceRequest {
    @NotBlank
    private String serialNumber;
}
