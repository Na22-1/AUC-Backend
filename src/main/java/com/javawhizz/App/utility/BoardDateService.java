package com.javawhizz.App.utility;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.entity.BoardDate;
import com.javawhizz.App.entity.BoardKey;
import com.javawhizz.App.repository.BoardDateRepository;
import com.javawhizz.App.repository.BoardKeyRepository;
import com.javawhizz.App.service.AucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BoardDateService {
    @Autowired
    private BoardDateRepository boardDateRepository;
    @Autowired
    private BoardKeyRepository boardKeyRepository;
    @Autowired
    private AucService aucService;

    public List<BoardDate> checkDateWithKey(String boardKey, String boardDate) {
        var key = boardKeyRepository.getKeyByBoardKey(boardKey);

        if (key == null) {
            throw new IllegalArgumentException("BoardKey not found: " + boardKey);
        }

        // Create and save a new BoardDate entry if it doesn't exist
        BoardDate newBoardDate = new BoardDate();
        newBoardDate.setBoardKeyEntity(key);
        newBoardDate.setBoardDate(boardDate);
        boardDateRepository.save(newBoardDate);
        // Return an empty Optional to indicate a new date was created
        return  Collections.emptyList();

    }


    public List<AucItem> getDateWithKey(String boardKey, String boardDate) {
        return aucService.getAucItems(boardKey, boardDate);
    }

    private BoardDate findBoardDate(String boardDateValue, BoardKey boardKeyValue) {
        return boardDateRepository.getBoardDateByBoardDateAndBoardKeyEntity(boardDateValue, boardKeyValue);
    }


}
