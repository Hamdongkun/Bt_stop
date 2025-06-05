package com.bt.shop.controller;

import com.bt.shop.model.Product;
import com.bt.shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.List;

// 📄 src/main/java/com/bt/shop/controller/SearchController.java
@Controller
public class SearchController {

    private final ProductService productService;

    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword, Model model) {
        List<Product> searchResults;
        if (keyword == null || keyword.trim().isEmpty()) {
            searchResults = productService.findAllProducts(); // 전체 상품
        } else {
            searchResults = productService.searchProductsByKeyword(keyword); // 키워드 검색
        }

        model.addAttribute("results", searchResults);
        model.addAttribute("keyword", keyword == null ? "전체" : keyword);
        return "search_page";  // search_page.html
    }
}
