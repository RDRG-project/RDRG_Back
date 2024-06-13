package com.rdrg.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rdrg.back.entity.DeviceEntity;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, String> {
    DeviceEntity findBySerialNumber(String serialNumber);
    List<DeviceEntity> findByOrderBySerialNumber();

    boolean existsBySerialNumber(String serialNumber);

    // @Query("SELECT ds FROM DevicesStatus ds WHERE ds.serialNumber NOT IN " +
    //                 "(SELECT rd.serialNumber FROM RentDetail rd WHERE rd.rentNumber IN " +
    //                 "(SELECT drs.rentNumber FROM DeviceRentStatus drs WHERE " +
    //                 "(drs.rentDatetime < :inputRentDatetime AND drs.rentReturnDatetime > :inputReturnDatetime) OR " +
    //                 "(drs.rentDatetime < :inputReturnDatetime AND drs.rentReturnDatetime > :inputReturnDatetime) OR " +
    //                 "(drs.rentDatetime > :inputRentDatetime AND drs.rentReturnDatetime < :inputReturnDatetime)))")
    // List<DeviceEntity> findAvailableDevices(@Param("inputRentDatetime") String inputRentDatetime,
    //     @Param("inputReturnDatetime") String inputReturnDatetime);

        @Query("SELECT ds FROM DevicesStatus ds WHERE ds.place = :inputPlace AND ds.serialNumber NOT IN " +
        "(SELECT rd.serialNumber FROM RentDetail rd WHERE rd.rentNumber IN " +
        "(SELECT drs.rentNumber FROM DeviceRentStatus drs WHERE " +
        "(drs.rentDatetime < :inputRentDatetime AND drs.rentReturnDatetime  > :inputRentReturnDatetime) OR " +
        "(drs.rentDatetime < :inputReturnDatetime AND drs.rentReturnDatetime  > :inputRentReturnDatetime) OR " +
        "(drs.rentDatetime > :inputRentDatetime AND drs.rentReturnDatetime  < :inputRentReturnDatetime)))")
List<DeviceEntity> findAvailableDevices(
    @Param("inputRentDatetime") String inputRentDatetime,
    @Param("inputReturnDatetime") String inputReturnDatetime,
    @Param("inputRentReturnDatetime") String inputRentReturnDatetime,
    @Param("inputPlace") String inputPlace);

    // @Query("SELECT ds FROM DevicesStatus ds " +
    //     "WHERE ds.serialNumber IN (SELECT rd.serialNumber FROM RentDetail rd WHERE rd.rentNumber = :rentNumber)")
    // List<DeviceEntity> findRentDevices(@Param("rentNumber") Integer rentNumber);
}
