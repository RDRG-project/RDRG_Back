package com.rdrg.back.entity;

import com.rdrg.back.dto.request.device.PostDeviceRequestDto;
import com.rdrg.back.dto.request.payment.PostPaymentRequestDto;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
    private String rentSerialNumber;
    private String rentDatetime;
    private String rentReturnDatetime;
    private String rentPlace;
    private String rentReturnPlace;
    private Integer rentTotalPrice;
    private boolean rentStatus;

    public DeviceRentStatusEntity (PostPaymentRequestDto dto, String userId, LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String writeDateTime = localDateTime.format(dateTimeFormatter);

        this.rentUserId = userId;
        this.rentSerialNumber = dto.getRentSerialNumber();
        this.rentDatetime = writeDateTime;
        this.rentReturnDatetime = writeDateTime;
        this.rentPlace = dto.getRentPlace();
        this.rentReturnPlace = dto.getRentReturnPlace();
        this.rentTotalPrice = dto.getRentTotalPrice();
        this.rentStatus = false;
    }
}
