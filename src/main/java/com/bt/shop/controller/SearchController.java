package com.bt.shop.controller;

import com.bt.shop.model.Product;
import com.bt.shop.model.SearchHistory;
import com.bt.shop.service.ProductService;
import com.bt.shop.repository.SearchHistoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class SearchController {

    private final ProductService productService;
    private final SearchHistoryRepository searchHistoryRepository;

    public SearchController(ProductService productService, SearchHistoryRepository searchHistoryRepository) {
        this.productService = productService;
        this.searchHistoryRepository = searchHistoryRepository;
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword, Model model) {
        Long userId = 1L; // 로그인 연동 전 임시 고정값

        List<Product> searchResults;
        if (keyword == null || keyword.trim().isEmpty()) {
            searchResults = productService.findAllProducts();
            model.addAttribute("keyword", "전체");
        } else {
            // 1. 검색어 저장
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setUserId(userId);
            searchHistory.setKeyword(keyword);
            searchHistory.setSearchedAt(LocalDateTime.now());
            searchHistoryRepository.save(searchHistory);

            // 2. 검색 실행
            searchResults = productService.searchProductsByKeyword(keyword);
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("searchResults", searchResults);
        return "search_page";  // 템플릿 파일명
    }
}
