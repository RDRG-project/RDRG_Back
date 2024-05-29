package com.rdrg.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rdrg.back.entity.UploadEntity;

@Repository
public interface UploadRepository extends JpaRepository<UploadEntity, Integer> {
    List<UploadEntity> findByLinkBoardNumber(int linkBoardNumber);
    List<UploadEntity> findByFileNumber(int fileNumber);
}
