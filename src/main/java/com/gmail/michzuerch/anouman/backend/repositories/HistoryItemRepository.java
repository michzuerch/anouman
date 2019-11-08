package com.gmail.michzuerch.anouman.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.michzuerch.anouman.backend.data.entity.HistoryItem;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
}
