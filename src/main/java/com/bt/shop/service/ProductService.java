package com.bt.shop.service;

import com.bt.shop.model.Product;
import com.bt.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getTop4BestProducts() {
        return productRepository.findTop4ByOrderByStockDesc();
    }

    public List<Product> searchProductsByKeyword(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);

    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }


    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
