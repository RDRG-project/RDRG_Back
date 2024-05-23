package com.rdrg.back.common.object;

import com.rdrg.back.entity.DeviceEntity;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;

@Getter

public class DeviceListItem {

    private String serialNumber;
    private String model;
    private String name;
    private String deviceExplain;
    private String type;
    private String brand;
    private Integer price;
    private String devicesImgUrl;
    

    private DeviceListItem(DeviceEntity deviceEntity) throws Exception {

        this.serialNumber = deviceEntity.getSerialNumber();
        this.model = deviceEntity.getModel();
        this.name = deviceEntity.getName();
        this.deviceExplain = deviceEntity.getDeviceExplain();
        this.type = deviceEntity.getType();
        this.brand = deviceEntity.getBrand();
        this.price = deviceEntity.getPrice();
        this.devicesImgUrl = deviceEntity.getDevicesImgUrl();

    }

    public static List<DeviceListItem> getList(List<DeviceEntity> deviceEntities) throws Exception {
        List<DeviceListItem> deviceList = new ArrayList<>();

        for(DeviceEntity deviceEntity: deviceEntities) {
            DeviceListItem deviceListItem = new DeviceListItem(deviceEntity);
            deviceList.add(deviceListItem);
        }

        return deviceList;


    } 
}
