package com.javawhizz.App.repository;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.entity.BoardDate;
import com.javawhizz.App.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardDateRepository extends JpaRepository<BoardDate, Long> {

    List<BoardDate> findByBoardEntityOrderByCreateDateAsc(Key boardKeyEntity);
    BoardDate findByCreateDateAndBoardEntity(String createDate, Key boardEntity);
    BoardDate getBoardDateByBoardEntity(Key boardEntity);

    BoardDate findByBoardEntityAndCreateDate(Key boardEntity, String createDate);

    boolean existsByBoardEntity_BoardKeyAndCreateDate(String boardKey, String createDate);
}
