package com.rdrg.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rdrg.back.entity.RentDetailEntity;

@Repository
public interface RentDetailRepository extends JpaRepository<RentDetailEntity, Integer> {
    
}
