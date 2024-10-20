package com.javawhizz.App.service;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.entity.BoardDate;
import com.javawhizz.App.entity.BoardKey;
import com.javawhizz.App.repository.AucItemRepository;
import com.javawhizz.App.repository.BoardDateRepository;
import com.javawhizz.App.repository.BoardKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AucService {
    @Autowired
    private AucItemRepository aucItemRepository;

    @Autowired
    private BoardKeyRepository boardKeyRepository;

    @Autowired
    private BoardDateRepository boardDateRepository;
    @Autowired
    private BoardLoginService boardLoginService;
    public List<AucItem> getAucItems(String boardKeyValue, String boardDateValue) {
        var boardKeyEntity = boardKeyRepository.findByBoardKey(boardKeyValue).getId();

        if (boardKeyEntity == null) {
            return Collections.emptyList();
        }

        BoardDate boardDateEntity = boardDateRepository.findByBoardKeyEntity_IdAndBoardDate(boardKeyEntity, boardDateValue);

        if (boardDateEntity != null) {
            return aucItemRepository.findByBoardDate(boardDateEntity);
        }

        return Collections.emptyList();
    }
 /*   public List<AucItem> getAucItems(String boardKey, String boardDate) {
        // Find the BoardDate entity by boardKey and boardDate
        BoardKey boardKeyEntity = new BoardKey(); // Create a new BoardKey entity
        boardKeyEntity.setBoardKey(boardKey); // Assuming your BoardKey entity has a field called 'key'

        BoardDate boardDateEntity = boardDateRepository.getBoardDateByBoardDateAndBoardKeyEntityBoardKey(boardDate, boardKeyEntity.toString());

        // If a match is found, return its associated AucItems
        if (boardDateEntity != null) {
            return boardDateEntity.getAucItemList();
        }

        // If no match is found, return null or empty list
        return null; // or Collections.emptyList();
    }
*/
    public AucItem getAucItem(Long id) {
        return aucItemRepository.findById(id).orElse(null);
    }

    public AucItem createAucItem(String boardKeyString, String boardDateString, AucItem aucItem) {
        // Fetch the BoardKey
        var boardKey = boardKeyRepository.findByBoardKey(boardKeyString).getId();
        var boardKey1 = boardKeyRepository.findByBoardKey(boardKeyString);
        var a = boardDateRepository.getBoardDateByBoardDateAndBoardKeyEntity(boardDateString, boardKey1);
        if (boardKey1 == null) {
            throw new IllegalArgumentException("Invalid board key.");
        }

        // Fetch the BoardDate
        BoardDate boardDate = boardDateRepository.findByBoardKeyEntity_IdAndBoardDate(boardKey, boardDateString);
        if (boardDate == null || !boardDate.getBoardKeyEntity().getId().equals(boardKey) ) {
            throw new IllegalArgumentException("Invalid board date for the given board key.");
        }

        // Associate AucItem with BoardDate
        aucItem.setBoardDate(boardDate);
        return aucItemRepository.save(aucItem);
    }

/*
    public AucItem createAucItem(AucItem aucItem, String boardKey, String boardDate) {
        BoardKey boardKeyEntity = boardKeyRepository.findByBoardKey(boardKey);

        aucItem.setBoardDate(boardDateRepository.getBoardDateByBoardDateAndBoardKeyEntity(boardDate, boardKeyEntity));
        return aucItemRepository.save(aucItem);
    }

    /**  public List<AucItem> getAucItemsByBoardKeySorted(String boardKey) {
     Key boardKeyEntity = keyRepository.findByBoardKey(boardKey);
     if (boardKeyEntity == null) {
     return new ArrayList<>(); // No such key
     }

     // Get board dates associated with this key and order them by date
     List<BoardDate> boardDates = boardDateRepository.findByBoardEntityOrderByCreateDateAsc(boardKeyEntity);

     // Collect and return all AucItems sorted by their board date
     List<AucItem> aucItems = new ArrayList<>();
     for (BoardDate boardDate : boardDates) {
     List<AucItem> itemsForDate = aucItemRepository.findByBoardDate(boardDate);
     aucItems.addAll(itemsForDate);
     }
     return aucItems;
     }
     */

    public AucItem updateAucItem(Long id, AucItem updatedAucItem) {
        AucItem existingAucItem = getAucItem(id);

        if (existingAucItem == null) {
            return null;
        }
        existingAucItem.setCanvasBox(updatedAucItem.getCanvasBox());
        existingAucItem.setDescription(updatedAucItem.getDescription());
        var save = aucItemRepository.save(existingAucItem);

        return save;
    }

    public void deleteAucItem(Long id) {
        aucItemRepository.deleteById(id);
    }

}