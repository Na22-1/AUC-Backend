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
    public List<AucItem> getAucItems(String boardKeyValue, String boardDateValue) {
        BoardKey boardKeyEntity = boardKeyRepository.findByBoardKey(boardKeyValue);

        if (boardKeyEntity == null) {
            return Collections.emptyList();
        }

        BoardDate boardDateEntity = boardDateRepository.findByBoardKeyEntityAndBoardDate(boardKeyEntity, boardDateValue);

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

    public AucItem createAucItem(AucItem aucItem, String boardKey) {
   //     aucItem.setBordEntity(boardKeyRepository.getKeyByBoardKey(boardKey));
     //   return aucRepository.save(aucItem);
        return null;
    }

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
}