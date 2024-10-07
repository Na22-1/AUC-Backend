package com.javawhizz.App.service;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.entity.BoardDate;
import com.javawhizz.App.entity.Key;
import com.javawhizz.App.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AucService {
    @Autowired
    private AucItemRepository aucRepository;

    @Autowired
    private BoardKeyRepository boardKeyRepository;

    @Autowired
    private BoardDateRepository boardDateRepository;

    /**
     * Fetch AucItems for a given boardKey sorted by their BoardDate's createDate
     */
    public List<AucItem> getAucItems(String boardKey, String date) {
        // Retrieve the Key entity by boardKey
        Key keyEntity = boardKeyRepository.getKeyByBoardKey(boardKey);

        if (keyEntity == null) {
            // If no board with the key exists, return null or throw an exception
            return null;
        }

        // Fetch the BoardDate associated with the key and the given date
        BoardDate boardDate = boardDateRepository.findByBoardEntityAndCreateDate(keyEntity, date);
        if (boardDate == null) {
            // If no board with the key and date exists, return null or throw an exception
            return null;
        }

        // Retrieve and return the AucItems associated with the BoardDate
        return aucRepository.findByBoardDate(boardDate);
    }

    public AucItem getAucItem(Long id) {
        return aucRepository.findById(id).orElse(null);
    }

    /**
     * Create a new AucItem and associate it with a boardKey and a BoardDate
     */
    public AucItem createAucItem(AucItem aucItem, String boardKey, String date) {
        // Find or create the Key entity
        Key keyEntity = boardKeyRepository.getKeyByBoardKey(boardKey);
        if (keyEntity == null) {
            throw new IllegalArgumentException("Board Key does not exist");
        }

        // Find or create the BoardDate entity for the key and date
        BoardDate boardDate = boardDateRepository.findByBoardEntityAndCreateDate(keyEntity, date);
        if (boardDate == null) {
            // If no board exists for the key and date, create a new one
            boardDate = new BoardDate();
            boardDate.setCreateDate(date);
            boardDate.setBoardEntity(keyEntity);
            boardDateRepository.save(boardDate);
        }

        // Associate the AucItem with the found/created BoardDate
        aucItem.setBoardDate(boardDate);
        return aucRepository.save(aucItem);
    }

    public AucItem updateAucItem(Long id, AucItem updatedAucItem) {
        AucItem existingAucItem = getAucItem(id);

        if (existingAucItem == null) {
            return null;
        }

        existingAucItem.setCanvasBox(updatedAucItem.getCanvasBox());
        existingAucItem.setDescription(updatedAucItem.getDescription());
        existingAucItem.setBoardName(updatedAucItem.getBoardName());
        existingAucItem.setBoardDate(updatedAucItem.getBoardDate());

        return aucRepository.save(existingAucItem);
    }

    public void deleteAucItem(Long id) {
        aucRepository.deleteById(id);
    }

    public List<AucItem> loginBoard(String boardKey, String createDate){
        var key = boardKeyRepository.getKeyByBoardKey(boardKey);
        if (key != null){
            var date = boardDateRepository.getBoardDateByBoardEntity(key);
            return date.getAucItemList();
        }
        return null;
    }

    /**
     * Create a new board with a unique key.
     */
    public boolean createBoardKey(String boardKey) {
        if (boardKeyRepository.existsByBoardKey(boardKey)) {
            // If the key already exists, return false
            return false;
        }

        // Create a new board key if it doesn't exist
        Key entity = new Key();
        entity.setBoardKey(boardKey);
        boardKeyRepository.save(entity);

        return true;
    }

    /**
     * Method to get or create a new board date for an existing key.
     */
    public BoardDate createOrGetBoardDate(String boardKey, String date) {
        Key keyEntity = boardKeyRepository.getKeyByBoardKey(boardKey);
        if (keyEntity == null) {
            throw new IllegalArgumentException("Board key does not exist.");
        }

        // Check if a board with the same key and date exists
        BoardDate boardDate = boardDateRepository.findByBoardEntityAndCreateDate(keyEntity, date);
        if (boardDate == null) {
            // Create a new BoardDate if it doesn't exist
            boardDate = new BoardDate();
            boardDate.setCreateDate(date);
            boardDate.setBoardEntity(keyEntity);
            boardDateRepository.save(boardDate);
        }

        return boardDate;
    }
}

