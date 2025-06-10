package com.bt.shop.repository;

import com.bt.shop.model.RecentView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecentViewRepository extends JpaRepository<RecentView, Long> {
    List<RecentView> findTop4ByUserIdOrderByViewedAtDesc(Long userId);
}
