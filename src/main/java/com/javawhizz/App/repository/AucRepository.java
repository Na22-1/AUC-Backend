package com.javawhizz.App.repository;

import com.javawhizz.App.entity.AucItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AucRepository extends JpaRepository<AucItem, Long> {
    List<AucItem> findAucItemsByBordEntityId(Long keyId);
}