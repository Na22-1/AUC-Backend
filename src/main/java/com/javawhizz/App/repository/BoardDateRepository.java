package com.javawhizz.App.repository;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.entity.BoardDate;
import com.javawhizz.App.entity.BoardKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardDateRepository extends JpaRepository<BoardDate, Long> {
    List<BoardDate> findByBoardKeyEntity(BoardKey boardKeyEntity);

    BoardDate getBoardDateByBoardDateAndBoardKeyEntity(String boardDate, BoardKey boardKeyEntity);

    BoardDate findByBoardKeyEntity_IdAndBoardDate(Long boardKeyId, String boardDate);

    BoardDate findByBoardDateAndBoardKeyEntity(String boardDate, BoardKey boardKeyEntity);
    BoardDate findBoardDateByBoardKeyEntity(BoardKey boardKeyEntity);

}
