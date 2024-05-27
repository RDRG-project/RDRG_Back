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

    public RentDetailEntity(Integer rentNumber, String serialNumber) {
        this.rentNumber = rentNumber;
        this.serialNumber = serialNumber;
    }
}
