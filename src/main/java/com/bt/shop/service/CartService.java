package com.bt.shop.service;

import com.bt.shop.model.CartItem;
import com.bt.shop.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void addToCart(Long userId, Long productId, int quantity) {
        // 기존에 동일한 상품이 있으면 수량만 증가시키는 것도 가능함 → 여기서는 단순 insert
        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);
        cartItem.setAddedAt(LocalDateTime.now());

        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }
}
