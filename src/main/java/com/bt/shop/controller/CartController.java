package com.bt.shop.controller;

import com.bt.shop.model.CartItemViewModel;
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
        List<CartItemViewModel> cartItems = cartService.getCartItemViewModels(userId);
        int totalPrice = cartService.calculateTotalPrice(userId);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        return "cart"; // cart.html 사용
    }

    // 장바구니에 상품 추가
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        Long userId = 1L; // 테스트용 user_id 고정
        cartService.addToCart(userId, productId, quantity);
        return "redirect:/cart";
    }
    // 수량 증가
    @PostMapping("/increase")
    public String increaseQuantity(@RequestParam Long productId) {
        Long userId = 1L;
        cartService.addToCart(userId, productId, 1); // 기존 addToCart 재사용 가능
        return "redirect:/cart";
    }

    // 수량 감소
    @PostMapping("/decrease")
    public String decreaseQuantity(@RequestParam Long productId) {
        Long userId = 1L;
        cartService.decreaseQuantity(userId, productId);
        return "redirect:/cart";
    }

    // 상품 삭제
    @PostMapping("/delete")
    public String deleteItem(@RequestParam Long productId) {
        Long userId = 1L;
        cartService.deleteItem(userId, productId);
        return "redirect:/cart";
    }

    // 장바구니 전체 삭제
    @PostMapping("/clear")
    public String clearCart() {
        Long userId = 1L;
        cartService.clearCart(userId);
        return "redirect:/cart";
    }

}
