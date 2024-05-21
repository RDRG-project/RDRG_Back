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
    private String explain;
    private String type;
    private Integer price;
    private String devicesImgUrl;
    

    private DeviceListItem(DeviceEntity deviceEntity) throws Exception {

        this.serialNumber = deviceEntity.getSerialNumber();
        this.model = deviceEntity.getModel();
        this.name = deviceEntity.getName();
        this.explain = deviceEntity.getExplain();
        this.type = deviceEntity.getType();
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
