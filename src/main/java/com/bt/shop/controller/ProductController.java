package com.bt.shop.controller;

import com.bt.shop.model.Product;
import com.bt.shop.model.RecentView;
import com.bt.shop.repository.RecentViewRepository;
import com.bt.shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@Controller
public class ProductController {

    private final ProductService productService;
    private final RecentViewRepository recentViewRepository;

    public ProductController(ProductService productService, RecentViewRepository recentViewRepository) {
        this.productService = productService;
        this.recentViewRepository = recentViewRepository;
    }

    @GetMapping("/product/{productId}")
    public String productDetail(@PathVariable Long productId, Model model) {
        Long userId = 1L; // 추후 로그인 사용자로 변경

        // 상품 조회
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);

        // 최근 본 상품 기록 저장
        RecentView recentView = new RecentView();
        recentView.setUserId(userId);
        recentView.setProductId(productId);
        recentView.setViewedAt(LocalDateTime.now());
        recentViewRepository.save(recentView);

        return "product_detail"; // 해당 페이지의 Thymeleaf 템플릿명
    }
}
