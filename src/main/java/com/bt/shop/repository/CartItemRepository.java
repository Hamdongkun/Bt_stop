package com.bt.shop.repository;

import com.bt.shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserId(@Param("userId") Long userId);
    @Transactional
    void deleteByUserId(Long userId);
}
