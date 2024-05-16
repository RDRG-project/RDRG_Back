package com.rdrg.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "deviceRentStatus")
@Table(name = "device_rent_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRentStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rentNumber;
    private String rentUserId;
    private String rentSerial;
    private String rentDatetime;
    private String rentReturnDatetime;
    private String rentPlace;
    private String rentReturnPlace;
    private Integer rentTotalPrice;
    private boolean rentStatus;
}
