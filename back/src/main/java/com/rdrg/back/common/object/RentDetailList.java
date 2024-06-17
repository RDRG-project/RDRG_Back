package com.rdrg.back.common.object;

import java.util.List;
import java.util.ArrayList;

import com.rdrg.back.entity.RentDetailEntity;

import lombok.Getter;

@Getter
public class RentDetailList {
    private String name;
    private Integer price;

    private RentDetailList(RentDetailEntity rentDetailEntity) throws Exception {
        this.name = rentDetailEntity.getName();
        this.price = rentDetailEntity.getPrice();
    }

    public static List<RentDetailList> getNamePriceList(List<RentDetailEntity> rentDetailEntities) throws Exception {
        List<RentDetailList> rentDetailLists = new ArrayList<>();

        for (RentDetailEntity rentDetailEntity : rentDetailEntities) {
            RentDetailList rentDetailList = new RentDetailList(rentDetailEntity);
            rentDetailLists.add(rentDetailList);
        }
        return rentDetailLists;
    }
}
