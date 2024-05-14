package com.javawhizz.App.service;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.entity.Key;
import com.javawhizz.App.repository.AucRepository;
import com.javawhizz.App.repository.BoardKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AucService {
    @Autowired
    private AucRepository aucRepository;

    @Autowired
    private BoardKeyRepository boardKeyRepository;

    public List<AucItem> getAucItems(String boardKey) {
        Long key = boardKeyRepository.getKeyByBoardKey(boardKey).getId();
        return aucRepository.findAucItemsByBordEntityId(key);
    }

    public AucItem getAucItem(Long id) {
        return aucRepository.findById(id).orElse(null);
    }

    public AucItem createAucItem(AucItem aucItem, String boardKey) {
        aucItem.setBordEntity(boardKeyRepository.getKeyByBoardKey(boardKey));
        return aucRepository.save(aucItem);
    }

    public AucItem updateAucItem(Long id, AucItem updatedAucItem) {
        AucItem existingAucItem = getAucItem(id);

        if (existingAucItem == null) {
            return null;
        }
        existingAucItem.setCanvasBox(updatedAucItem.getCanvasBox());
        existingAucItem.setDescription(updatedAucItem.getDescription());
        var save = aucRepository.save(existingAucItem);

        return save;
    }

    public void deleteAucItem(Long id) {
        aucRepository.deleteById(id);
    }

    public List<AucItem> loginBoard(String boardKey){
        var key = boardKeyRepository.getKeyByBoardKey(boardKey);
        if (key != null){
            return key.getAucItemList();
        }
        return null;
    }
    public boolean createBoardKey(String boardKey){
        if (boardKeyRepository.existsByBoardKey(boardKey)){
            return false;
        }
        var entity = new Key();
        entity.setBoardKey(boardKey);
        boardKeyRepository.save(entity);

        return true;
    }
}