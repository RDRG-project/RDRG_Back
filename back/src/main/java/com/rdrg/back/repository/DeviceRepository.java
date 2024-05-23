package com.rdrg.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rdrg.back.entity.DeviceEntity;

import java.util.List;

@Repository

public interface DeviceRepository extends JpaRepository<DeviceEntity, String> {
    
    boolean existsBySerialNumber(String serialNumber);

    DeviceEntity findBySerialNumber(String serialNumber);

    List<DeviceEntity> findByOrderBySerialNumber();

    @Query("SELECT d FROM devices_status d WHERE d.serialNumber NOT IN (" +
        "  SELECT drs.rentSerialNumber FROM device_rent_status drs WHERE " +
        "    (drs.rentDatetime < :inputRentDatetime AND drs.rentReturnDatetime > :inputReturnDatetime) OR " +
        "    (drs.rentDatetime < :inputReturnDatetime AND drs.rentReturnDatetime > :inputReturnDatetime) OR " +
        "    (drs.rentDatetime > :inputRentDatetime AND drs.rentReturnDatetime < :inputReturnDatetime))")
    List<DeviceEntity> findAvailableDevices(@Param("inputRentDatetime") String inputRentDatetime,
        @Param("inputReturnDatetime") String inputReturnDatetime);
}
