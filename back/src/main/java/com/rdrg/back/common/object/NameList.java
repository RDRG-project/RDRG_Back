package com.rdrg.back.common.object;

import java.util.List;
import java.util.ArrayList;

import com.rdrg.back.entity.DeviceEntity;

import lombok.Getter;

@Getter
public class NameList {
    private String name;

    private NameList(DeviceEntity deviceEntity) throws Exception {
        this.name = deviceEntity.getName();
    }

    public static List<NameList> getNameList(List<DeviceEntity> deviceEntities) throws Exception {
        List<NameList> nameLists = new ArrayList<>();

        for (DeviceEntity deviceEntity : deviceEntities) {
            NameList nameList = new NameList(deviceEntity);
            nameLists.add(nameList);
        }
        return nameLists;
    }
}
