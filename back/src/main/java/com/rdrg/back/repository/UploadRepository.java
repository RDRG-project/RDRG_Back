package com.rdrg.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rdrg.back.entity.UploadEntity;

@Repository
public interface UploadRepository extends JpaRepository<UploadEntity, Integer> {
    
}
