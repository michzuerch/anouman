package com.gmail.michael.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.michael.backend.data.entity.HistoryItem;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
}
