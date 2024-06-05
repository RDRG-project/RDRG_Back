package com.rdrg.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="RentDetail")
@Table(name="rent_detail")
@Getter
@Setter
@NoArgsConstructor
public class RentDetailEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer rentDetailNumber;
    private Integer rentNumber;
    private String serialNumber;
    private String name;
    private Integer price;

    public RentDetailEntity(Integer rentNumber, DeviceEntity deviceEntity) {
        this.rentNumber = rentNumber;
        this.serialNumber = deviceEntity.getSerialNumber();
        this.name = deviceEntity.getName();
        this.price = deviceEntity.getPrice();
    }
}
