package com.rdrg.back.common.object;

import java.util.ArrayList;
import java.util.List;

import com.rdrg.back.entity.DeviceRentStatusEntity;

import lombok.Getter;

@Getter
public class RentItem {
    private String rentDatetime;
    private String rentReturnDatetime;
    private boolean rentStatus;

    private RentItem(DeviceRentStatusEntity deviceRentStatusEntity, boolean rentStatus) throws Exception {
        this.rentDatetime = deviceRentStatusEntity.getRentDatetime();
        this.rentReturnDatetime = deviceRentStatusEntity.getRentReturnDatetime();
        this.rentStatus = rentStatus;
    }

    public static List<RentItem> getRentList(List<DeviceRentStatusEntity> deviceRentStatusEntities, boolean rentStatus) throws Exception {
        List<RentItem> rentItems = new ArrayList<>();

        for (DeviceRentStatusEntity deviceRentStatusEntity : deviceRentStatusEntities) {
            RentItem rentItem = new RentItem(deviceRentStatusEntity, rentStatus);
            rentItems.add(rentItem);
        }
        return rentItems;
    }
}
