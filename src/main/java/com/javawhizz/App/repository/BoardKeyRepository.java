package com.javawhizz.App.repository;

import com.javawhizz.App.entity.BoardKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardKeyRepository extends JpaRepository<BoardKey, Long> {
    BoardKey findByBoardKey(String boardKey);
    boolean existsByBoardKey(String boardKey);
    BoardKey getKeyByBoardKey(String bordKey);


}
