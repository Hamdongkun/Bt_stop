package com.bt.shop.service;

import com.bt.shop.model.Product;
import com.bt.shop.model.RecentView;
import com.bt.shop.model.SearchHistory;
import com.bt.shop.repository.ProductRepository;
import com.bt.shop.repository.RecentViewRepository;
import com.bt.shop.repository.SearchHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RecentViewRepository recentViewRepository;
    private final SearchHistoryRepository searchHistoryRepository;

    public ProductService(ProductRepository productRepository,
                          RecentViewRepository recentViewRepository,
                          SearchHistoryRepository searchHistoryRepository) {
        this.productRepository = productRepository;
        this.recentViewRepository = recentViewRepository;
        this.searchHistoryRepository = searchHistoryRepository;
    }

    // 베스트 상품
    public List<Product> getTop4BestProducts() {
        return productRepository.findTop4ByOrderByStockDesc();
    }

    // 검색 기능
    public List<Product> searchProductsByKeyword(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    // 전체 상품
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // 상품 상세 조회
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // 최근 본 상품 기반 추천
    public List<Product> getRecentViewedProducts(Long userId) {
        List<RecentView> recentViews = recentViewRepository.findTop4ByUserIdOrderByViewedAtDesc(userId);
        List<Long> productIds = recentViews.stream()
                .map(RecentView::getProductId)
                .collect(Collectors.toList());
        return productRepository.findAllById(productIds);
    }

    // 최근 검색 기반 추천
    public List<Product> getSearchBasedRecommendedProducts(Long userId) {
        List<SearchHistory> searchHistories = searchHistoryRepository.findTop3ByUserIdOrderBySearchedAtDesc(userId);
        List<String> keywords = searchHistories.stream()
                .map(SearchHistory::getKeyword)
                .collect(Collectors.toList());
        if (keywords.isEmpty()) {
            return Collections.emptyList();
        }
        Set<Product> result = new LinkedHashSet<>(); // 중복 제거 + 순서 유지

        for (String keyword : keywords) {
            List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
            result.addAll(products);
        }

        // 최대 4개만 반환
        return result.stream().limit(4).collect(Collectors.toList());
    }

    // 사용자 맞춤 추천 (최근 본 + 최근 검색 기반 합친 추천)
    public List<Product> getRecommendedProductsForUser(Long userId) {
        List<Product> basedOnRecentView = getRecentViewedProducts(userId);
        List<Product> basedOnRecentSearch = getSearchBasedRecommendedProducts(userId);

        // 두 리스트 합치기 (중복 제거, 순서 유지)
        Set<Product> combined = new LinkedHashSet<>();
        combined.addAll(basedOnRecentView);
        combined.addAll(basedOnRecentSearch);

        return combined.stream().limit(4).toList();
    }
}
