package com.javawhizz.App.repository;

import com.javawhizz.App.entity.BoardDate;
import com.javawhizz.App.entity.BoardKey;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardDateRepository extends JpaRepository<BoardDate, Long> {
    BoardDate getBoardDateByBoardDateAndBoardKeyEntity(String boardDate, BoardKey boardKeyEntity);

    BoardDate findByBoardKeyEntity_IdAndBoardDate(Long boardKeyId, String boardDate);

}
