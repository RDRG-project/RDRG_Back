package com.rdrg.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rdrg.back.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    
    List<BoardEntity> findByOrderByReceptionNumberDesc();
    List<BoardEntity> findByTitleContainsOrderByReceptionNumberDesc(String title);
    BoardEntity findByReceptionNumber(Integer receptionNumber);
}
