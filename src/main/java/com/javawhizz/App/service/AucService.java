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

    public AucItem getAucItem(Long id) {
        return aucItemRepository.findById(id).orElse(null);
    }

    public AucItem createAucItem(String boardKey, String boardDate, AucItem aucItem) {
        BoardKey keyEntity = boardKeyRepository.findByBoardKey(boardKey);
        if (keyEntity == null) {
            throw new IllegalArgumentException("Invalid board key: " + boardKey);
        }

        BoardDate date = boardDateRepository.findBoardDateByBoardKeyEntity(keyEntity);
        aucItem.setBoardKeyEntity(keyEntity);
        aucItem.setBoardDate(date);

        return aucItemRepository.save(aucItem);
    }

    public AucItem updateAucItem(Long id, AucItem updatedAucItem) {
        AucItem existingAucItem = getAucItem(id);

        if (existingAucItem == null) {
            return null;
        }
        existingAucItem.setCanvasBox(updatedAucItem.getCanvasBox());
        existingAucItem.setDescription(updatedAucItem.getDescription());

        return aucItemRepository.save(existingAucItem);
    }

    public void deleteAucItem(Long id) {
        aucItemRepository.deleteById(id);
    }

}