package com.bt.shop.repository;

import com.bt.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop4ByOrderByStockDesc();

    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}

