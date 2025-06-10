package com.bt.shop.repository;

import com.bt.shop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder_UserId(Long userId);
    List<OrderItem> findByProductId(Long productId);
    List<OrderItem> findByOrderId(Long orderId);
}

