package com.javawhizz.App.service;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.entity.BoardDate;
import com.javawhizz.App.entity.Key;
import com.javawhizz.App.repository.AucItemRepository;
import com.javawhizz.App.repository.BoardDateRepository;
import com.javawhizz.App.repository.BoardKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardAucService {

    @Autowired
    private AucItemRepository aucItemRepository;

    @Autowired
    private BoardKeyRepository keyRepository;

    @Autowired
    private BoardDateRepository boardDateRepository;

    /**
     * Create a new board with a unique key. If the key already exists,
     * it will return false.
     */
    public boolean createBoardKey(String boardKey) {
        if (keyRepository.existsByBoardKey(boardKey)) {
            return false; // Key already exists
        }
        Key newKey = new Key();
        newKey.setBoardKey(boardKey);
        keyRepository.save(newKey);
        return true;
    }

    /**
     * Create a new board date associated with an existing board key.
     * If the board key doesn't exist, return null.
     */

    public boolean createBoardDate(String boardKey, String createDate) {
        // Check if the combination of boardKey and createDate exists
        if (boardDateRepository.existsByBoardEntity_BoardKeyAndCreateDate(boardKey, createDate)) {
            return false; // The combination already exists
        }

        // If the boardKey exists but the date is new, create a new BoardDate
        Key existingKey = keyRepository.findByBoardKey(boardKey);

        if (existingKey == null) {
            return false; // Board key does not exist in the keyRepository
        }

        // Create new board date
        var newBoardDate = new BoardDate();
        newBoardDate.setCreateDate(createDate);
        newBoardDate.setBoardEntity(existingKey);
        boardDateRepository.save(newBoardDate);

        return true; // Successfully created new board date
    }

    /**
     * Fetch AucItems by board key and return them sorted by their associated board dates.
     */
    public List<AucItem> getAucItemsByBoardKeySorted(String boardKey) {
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

    /**
     * Fetch AucItems by board key and specific board date.
     */
    public List<AucItem> getAucItemsByBoardKeyAndDate(String boardKey, String createDate) {
        Key boardKeyEntity = keyRepository.findByBoardKey(boardKey);
        if (boardKeyEntity == null) {
            return new ArrayList<>(); // No such key
        }

        // Fetch board date by key and the specific date
        BoardDate boardDate = boardDateRepository.findByBoardEntityAndCreateDate(boardKeyEntity, createDate);
        if (boardDate == null) {
            return new ArrayList<>(); // No board date for the key and date
        }

        // Return AucItems associated with this board date
        return aucItemRepository.findByBoardDate(boardDate);
    }

    /**
     * Create a new AucItem associated with a specific board key and date.
     */
    public AucItem createAucItem(AucItem aucItem, String boardKey, String createDate) {
        Key boardKeyEntity = keyRepository.findByBoardKey(boardKey);
        if (boardKeyEntity == null) {
            return null; // Key not found
        }

        BoardDate boardDate = boardDateRepository.findByBoardEntityAndCreateDate(boardKeyEntity, createDate);
        if (boardDate == null) {
            return null; // Date not found for the given key
        }

        // Associate the AucItem with the board date
        aucItem.setBoardDate(boardDate);
        return aucItemRepository.save(aucItem);
    }

    /**
     * Update an existing AucItem.
     */
    public AucItem updateAucItem(Long id, AucItem updatedAucItem) {
        AucItem existingAucItem = aucItemRepository.findById(id).orElse(null);
        if (existingAucItem == null) {
            return null; // AucItem doesn't exist
        }

        // Update properties
        existingAucItem.setCanvasBox(updatedAucItem.getCanvasBox());
        existingAucItem.setDescription(updatedAucItem.getDescription());
        existingAucItem.setBoardName(updatedAucItem.getBoardName());
        existingAucItem.setBoardDate(updatedAucItem.getBoardDate());

        return aucItemRepository.save(existingAucItem);
    }

    /**
     * Delete an AucItem by ID.
     */
    public void deleteAucItem(Long id) {
        aucItemRepository.deleteById(id);
    }

    /**
     * Login to the board by the board key and retrieve all associated AucItems.
     */
    public List<AucItem> loginBoard(String boardKey) {
        Key key = keyRepository.findByBoardKey(boardKey);
        if (key != null) {
            return getAucItemsByBoardKeySorted(boardKey);
        }
        return new ArrayList<>();
    }
}
