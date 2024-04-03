package com.controller.repository;

import com.controller.entity.AucItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AucRepository extends JpaRepository<AucItem, Long> {
}