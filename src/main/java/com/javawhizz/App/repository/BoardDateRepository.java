package com.javawhizz.App.repository;

import com.javawhizz.App.entity.BoardDate;
import com.javawhizz.App.entity.BoardKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardDateRepository extends JpaRepository<BoardDate, Long> {
    List<BoardDate> findByBoardKeyEntity(BoardKey boardKeyEntity);
    BoardDate findByBoardKeyEntityAndBoardDate(BoardKey boardKey, String boardDate);
    BoardDate getBoardDateByBoardDateAndBoardKeyEntity(String boardDate, BoardKey boardKey);

}
