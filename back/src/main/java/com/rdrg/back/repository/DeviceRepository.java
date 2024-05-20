package com.rdrg.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rdrg.back.entity.DeviceEntity;
import java.util.List;

@Repository

public interface DeviceRepository extends JpaRepository<DeviceEntity, String> {
    
    boolean existsBySerialNumber(String serialNumber);

    DeviceEntity findBySerialNumber(String serialNumber);

    List<DeviceEntity> findByOrderBySerialNumber();
}
