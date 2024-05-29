package com.rdrg.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rdrg.back.entity.DeviceRentStatusEntity;

@Repository
public interface PaymentRepository extends JpaRepository<DeviceRentStatusEntity, Integer> {
    List<DeviceRentStatusEntity> findByRentUserIdOrderByRentNumberDesc(String rentUserId);
    DeviceRentStatusEntity findTop1ByRentUserIdOrderByRentNumberDesc(String rentUserId);
    DeviceRentStatusEntity findByRentUserIdOrderByRentNumber(String rentUserId);
}
