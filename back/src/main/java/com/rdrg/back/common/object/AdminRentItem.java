package com.rdrg.back.common.object;

import java.util.List;
import java.util.ArrayList;

import com.rdrg.back.entity.DeviceRentStatusEntity;
import com.rdrg.back.entity.RentDetailEntity;

import lombok.Getter;

@Getter
public class AdminRentItem {
    private Integer rentNumber;
    private String userId;
    private List<String> name;
    private String rentDatetime;
    private String rentReturnDatetime;
    private Integer totalPrice;
    private String rentStatus;

    public AdminRentItem(DeviceRentStatusEntity deviceRentStatusEntity, List<RentDetailEntity> rentDetailEntities) throws Exception {
        List<String> name = new ArrayList<>();
        for (RentDetailEntity rentDetailEntity: rentDetailEntities) name.add(rentDetailEntity.getName());

        this.rentNumber = deviceRentStatusEntity.getRentNumber();
        this.userId = deviceRentStatusEntity.getRentUserId();
        this.name = name;
        this.rentDatetime = deviceRentStatusEntity.getRentDatetime();
        this.rentReturnDatetime = deviceRentStatusEntity.getRentReturnDatetime();
        this.totalPrice = deviceRentStatusEntity.getRentTotalPrice();
        this.rentStatus = deviceRentStatusEntity.getRentStatus();
    }
}
