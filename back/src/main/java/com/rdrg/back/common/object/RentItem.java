package com.rdrg.back.common.object;

import java.util.ArrayList;
import java.util.List;

import com.rdrg.back.entity.DeviceEntity;
import com.rdrg.back.entity.DeviceRentStatusEntity;

import lombok.Getter;

@Getter
public class RentItem {
    private Integer rentNumber;
    private List<String> name;
    private String rentDatetime;
    private String rentReturnDatetime;
    private Integer totalPrice;
    private String rentStatus;

    public RentItem(DeviceRentStatusEntity deviceRentStatusEntity, List<DeviceEntity> deviceEntities) throws Exception {
        List<String> name = new ArrayList<>();
        for (DeviceEntity deviceEntity: deviceEntities) name.add(deviceEntity.getName());

        this.rentNumber = deviceRentStatusEntity.getRentNumber();
        this.name = name;
        this.rentDatetime = deviceRentStatusEntity.getRentDatetime();
        this.rentReturnDatetime = deviceRentStatusEntity.getRentReturnDatetime();
        this.totalPrice = deviceRentStatusEntity.getRentTotalPrice();
        this.rentStatus = deviceRentStatusEntity.getRentStatus();
    }

    // public static List<RentItem> getRentList(List<DeviceRentStatusEntity> deviceRentStatusEntities, List<String> name, boolean rentStatus) throws Exception {
        
    //     List<RentItem> rentItems = new ArrayList<>();

    //     for (DeviceRentStatusEntity deviceRentStatusEntity : deviceRentStatusEntities) {
    //         RentItem rentItem = new RentItem(deviceRentStatusEntity, nameLists, rentStatus);
    //         rentItems.add(rentItem);
    //     }
    //     return rentItems;
    // }
}
