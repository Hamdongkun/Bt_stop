package com.bt.shop.repository;

import com.bt.shop.model.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findTop3ByUserIdOrderBySearchedAtDesc(Long userId);
}
