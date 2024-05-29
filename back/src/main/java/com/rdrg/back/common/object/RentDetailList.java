package com.rdrg.back.common.object;

import java.util.List;
import java.util.ArrayList;

import com.rdrg.back.entity.DeviceEntity;

import lombok.Getter;

@Getter
public class RentDetailList {
    private String name;
    private Integer price;

    private RentDetailList(DeviceEntity deviceEntity) throws Exception {
        this.name = deviceEntity.getName();
        this.price = deviceEntity.getPrice();
    }

    public static List<RentDetailList> getNamePriceList(List<DeviceEntity> deviceEntities) throws Exception {
        List<RentDetailList> rentDetailLists = new ArrayList<>();

        for (DeviceEntity deviceEntity : deviceEntities) {
            RentDetailList rentDetailList = new RentDetailList(deviceEntity);
            rentDetailLists.add(rentDetailList);
        }
        return rentDetailLists;
    }
}
