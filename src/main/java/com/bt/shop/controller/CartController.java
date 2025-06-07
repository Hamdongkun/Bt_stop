package com.bt.shop.controller;

import com.bt.shop.model.CartItem;
import com.bt.shop.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 장바구니 페이지
    @GetMapping
    public String cartPage(Model model) {
        Long userId = 1L; // 테스트용 user_id 고정 (로그인 기능 붙으면 변경 가능)
        List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);
        model.addAttribute("cartItems", cartItems);
        return "cart"; // cart.html 사용
    }

    // 장바구니에 상품 추가
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        Long userId = 1L; // 테스트용 user_id 고정
        cartService.addToCart(userId, productId, quantity);
        return "redirect:/cart";
    }
}
