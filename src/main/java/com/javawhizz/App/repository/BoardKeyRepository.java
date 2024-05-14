package com.javawhizz.App.repository;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardKeyRepository extends JpaRepository<Key, Long> {
    Key getKeyByBoardKey(String bordKey);
    boolean existsByBoardKey(String boardKey);



}
