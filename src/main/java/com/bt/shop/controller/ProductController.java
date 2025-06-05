package com.bt.shop.controller;

import com.bt.shop.model.Product;
import com.bt.shop.model.Review;
import com.bt.shop.repository.ProductRepository;
import com.bt.shop.repository.ReviewRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ProductController(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    // 상세 페이지
    @GetMapping("/product/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품 없음"));

        List<Review> reviews = reviewRepository.findByProductId(id);

        double avgRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("avgRating", avgRating);

        return "product_detail";  // templates/product_detail.html
    }

    // 리뷰 저장
    @PostMapping("/product/{id}/review")
    public String submitReview(@PathVariable Long id,
                               @ModelAttribute("newReview") Review review) {
        review.setProductId(id);
        review.setCreateAt(LocalDateTime.now());
        review.setUserId(999L); // 테스트용 사용자 ID. 추후 로그인 정보에서 가져와야 함

        reviewRepository.save(review);
        return "redirect:/product/" + id;
    }
}
