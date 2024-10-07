package com.javawhizz.App.repository;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.entity.BoardDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AucItemRepository extends JpaRepository<AucItem, Long> {
    List<AucItem> findByBoardDate(BoardDate boardDate);

    List<AucItem> findAucItemsByBoardDate(BoardDate boardDate);
}