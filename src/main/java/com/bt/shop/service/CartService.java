package com.bt.shop.service;

import com.bt.shop.model.CartItem;
import com.bt.shop.model.CartItemViewModel;
import com.bt.shop.model.Product;
import com.bt.shop.repository.CartItemRepository;
import com.bt.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public List<CartItemViewModel> getCartItemViewModels(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        return cartItems.stream().map(cartItem -> {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + cartItem.getProductId()));

            CartItemViewModel viewModel = new CartItemViewModel();

            viewModel.setProductId(product.getProductId());
            viewModel.setProductName(product.getName());
            viewModel.setImageUrl(product.getImageUrl());
            viewModel.setQuantity(cartItem.getQuantity());
            viewModel.setUnitPrice(product.getPrice());
            viewModel.setLinePrice(product.getPrice() * cartItem.getQuantity());

            return viewModel;
        }).toList();
    }

    public int calculateTotalPrice(Long userId) {
        return getCartItemViewModels(userId).stream()
                .mapToInt(CartItemViewModel::getLinePrice)
                .sum();
    }

    public void addToCart(Long userId, Long productId, int quantity) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        CartItem existingItem = cartItems.stream()
                .filter(ci -> ci.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUserId(userId);
            newItem.setProductId(productId);
            newItem.setQuantity(quantity);
            newItem.setAddedAt(LocalDateTime.now());
            cartItemRepository.save(newItem);
        }
    }
    public void decreaseQuantity(Long userId, Long productId) {
        CartItem item = cartItemRepository.findByUserId(userId).stream()
                .filter(ci -> ci.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item != null) {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                cartItemRepository.save(item);
            } else {
                cartItemRepository.delete(item); // 수량 1개 → 삭제 처리
            }
        }
    }

    // 상품 삭제
    public void deleteItem(Long userId, Long productId) {
        cartItemRepository.findByUserId(userId).stream()
                .filter(ci -> ci.getProductId().equals(productId))
                .findFirst()
                .ifPresent(cartItemRepository::delete);
    }

    // 장바구니 전체 삭제
    public void clearCart(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        cartItemRepository.deleteAll(cartItems);
    }
}
