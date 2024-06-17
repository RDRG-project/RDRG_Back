package com.rdrg.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rdrg.back.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    BoardEntity findByReceptionNumber(Integer receptionNumber);
    List<BoardEntity> findByOrderByReceptionNumberDesc();
    List<BoardEntity> findByTitleContainsOrderByReceptionNumberDesc(String title);
}
