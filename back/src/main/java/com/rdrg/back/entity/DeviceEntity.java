package com.rdrg.back.entity;

import com.rdrg.back.dto.request.device.PostDeviceRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "device_status")
@Table(name = "device_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DeviceEntity {

    @Id
    private String serialNumber;
    private String Model;
    private String name;
    private String explain;
    private String type;
    private Integer price;


public DeviceEntity(PostDeviceRequestDto dto, String serialNumber) {

    this.serialNumber = dto.getSerialNumber();
    this.Model = dto.getModel();
    this.name = dto.getName();
    this.explain = dto.getExplain();
    this.type = dto.getType();
    this.price = dto.getPrice();
}

}