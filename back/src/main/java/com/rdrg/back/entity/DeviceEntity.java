package com.rdrg.back.entity;

import com.rdrg.back.dto.request.device.PostDeviceRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "DevicesStatus")
@Table(name = "devices_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEntity {
    @Id
    private String serialNumber;
    private String model;
    private String name;
    private String deviceExplain;
    private String type;
    private String brand;
    private Integer price;
    private String devicesImgUrl;

    public DeviceEntity(PostDeviceRequestDto dto, String serialNumber) {
        this.serialNumber = dto.getSerialNumber();
        this.model = dto.getModel();
        this.name = dto.getName();
        this.deviceExplain = dto.getDeviceExplain();
        this.type = dto.getType();
        this.brand = dto.getBrand();
        this.price = dto.getPrice();
        this.devicesImgUrl = dto.getDevicesImgUrl();
    }
}