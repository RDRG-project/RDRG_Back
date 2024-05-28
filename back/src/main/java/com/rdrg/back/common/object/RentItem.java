package com.rdrg.back.common.object;

import java.util.ArrayList;
import java.util.List;

import com.rdrg.back.entity.DeviceRentStatusEntity;

import lombok.Getter;

@Getter
public class RentItem {
    private Integer rentNumber;
    private List<NameList> name;
    private String rentDatetime;
    private String rentReturnDatetime;
    private Integer totalPrice;
    private boolean rentStatus;

    private RentItem(DeviceRentStatusEntity deviceRentStatusEntity, List<NameList> nameLists, boolean rentStatus) throws Exception {
        this.rentNumber = deviceRentStatusEntity.getRentNumber();
        this.name = nameLists;
        this.rentDatetime = deviceRentStatusEntity.getRentDatetime();
        this.rentReturnDatetime = deviceRentStatusEntity.getRentReturnDatetime();
        this.totalPrice = deviceRentStatusEntity.getRentTotalPrice();
        this.rentStatus = rentStatus;
    }

    public static List<RentItem> getRentList(List<DeviceRentStatusEntity> deviceRentStatusEntities, List<NameList> name, boolean rentStatus) throws Exception {
        
        List<RentItem> rentItems = new ArrayList<>();

        for (DeviceRentStatusEntity deviceRentStatusEntity : deviceRentStatusEntities) {
            RentItem rentItem = new RentItem(deviceRentStatusEntity, name, rentStatus);
            rentItems.add(rentItem);
        }
        return rentItems;
    }
}
