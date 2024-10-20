package com.javawhizz.App.service;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.entity.BoardDate;
import com.javawhizz.App.entity.BoardKey;
import com.javawhizz.App.repository.BoardDateRepository;
import com.javawhizz.App.repository.BoardKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BoardLoginService {

    @Autowired
    private BoardDateRepository boardDateRepository;

    @Autowired
    private BoardKeyRepository boardKeyRepository;

    public boolean createBoardKey(String boardKey) {
        if (boardKeyRepository.existsByBoardKey(boardKey)) {
            return false;
        }

        BoardKey entity = new BoardKey();
        entity.setBoardKey(boardKey);
        boardKeyRepository.save(entity);

        return true;
    }

    public List<AucItem> loginBoard(String boardKey, String boardDate) {
        var key = boardKeyRepository.getKeyByBoardKey(boardKey);

        if (key == null) {
            throw new IllegalArgumentException("BoardKey not found: " + boardKey);
        }

        BoardDate dateAndKey = findBoardDate(boardDate, key);

        if (dateAndKey != null) {
            return dateAndKey.getAucItemList();
        } else {
            BoardDate newBoardDate = new BoardDate();
            newBoardDate.setBoardKeyEntity(key);
            newBoardDate.setBoardDate(boardDate);
            boardDateRepository.save(newBoardDate);

            return Collections.emptyList();
        }
    }


    public BoardDate findBoardDate(String boardDateValue, BoardKey boardKeyValue) {
        return boardDateRepository.getBoardDateByBoardDateAndBoardKeyEntity(boardDateValue, boardKeyValue);
    }

}
