package com.rdrg.back.dto.request.device;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor


public class PostDeviceRequestDto {
    
    @NotBlank
    private String serialNumber;
    @NotBlank
    private String model;
    @NotBlank
    private String name;
    @NotBlank
    private String explain;
    @NotBlank
    private String type;
    @NotBlank
    private Integer price;
}
