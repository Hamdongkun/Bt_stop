package com.bt.shop.controller;

import com.bt.shop.model.CartItem;
import com.bt.shop.model.CartItemViewModel;
import com.bt.shop.model.Product;
import com.bt.shop.repository.CartItemRepository;
import com.bt.shop.repository.ProductRepository;
import com.bt.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService; // 추가됨

    @GetMapping("/payment")
    public String showPaymentPage(Model model) {

        Long userId = 1L; // 테스트용 고정

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        List<CartItemViewModel> cartItemViewModels = new ArrayList<>();
        int totalPrice = 0;

        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("상품 없음: " + item.getProductId()));

            CartItemViewModel viewModel = new CartItemViewModel();
            viewModel.setProductName(product.getName());
            viewModel.setImageUrl(product.getImageUrl());
            viewModel.setQuantity(item.getQuantity());
            viewModel.setUnitPrice(product.getPrice());
            viewModel.setLinePrice(product.getPrice() * item.getQuantity());


            totalPrice += viewModel.getLinePrice();

            cartItemViewModels.add(viewModel);
        }

        model.addAttribute("cartItems", cartItemViewModels);
        model.addAttribute("totalPrice", totalPrice);

        return "payment_page";
    }

    @PostMapping("/processOrder")
    public String processOrder(
            @RequestParam String receiver,
            @RequestParam String postcode,
            @RequestParam String address,
            @RequestParam String detailAddress,
            @RequestParam String phone,
            Model model)
    {
        Long userId = 1L; // 테스트용 고정

        // 주문 처리 + 장바구니 비우기
        orderService.createOrder(userId, receiver, postcode, address, detailAddress, phone);

        // 결제 완료 페이지로 이동
        return "redirect:/orderComplete";
    }

    @GetMapping("/orderComplete")
    public String showOrderCompletePage() {
        return "order_complete";
    }
}
