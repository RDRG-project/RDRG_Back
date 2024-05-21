package com.rdrg.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rdrg.back.entity.DeviceRentStatusEntity;

public interface PaymentRepository extends JpaRepository<DeviceRentStatusEntity, Integer> {
    
    List<DeviceRentStatusEntity> findByOrderByRentNumberDesc();
}
