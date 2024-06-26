package com.rdrg.back.dto.request.device;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String deviceExplain;
    @NotBlank
    private String type;
    @NotBlank
    private String brand;
    @NotNull
    private Integer price;
    private String devicesImgUrl;
    @NotNull
    private String place;
}
