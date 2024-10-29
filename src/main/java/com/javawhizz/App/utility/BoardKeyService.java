package com.javawhizz.App.utility;

import com.javawhizz.App.entity.BoardKey;
import com.javawhizz.App.repository.BoardKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardKeyService {
    @Autowired
    private BoardKeyRepository boardKeyRepository;

    public boolean createOrCheckBoardKey(String boardKey) {
        if (boardKeyRepository.existsByBoardKey(boardKey)) {
            return false;
        }
        createBoardKey(boardKey);
        return true;
    }

    private void createBoardKey(String boardKey) {
        BoardKey entity = new BoardKey();
        entity.setBoardKey(boardKey);
        boardKeyRepository.save(entity);
    }


}
