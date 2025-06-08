package com.bt.shop.service;

import com.bt.shop.model.CartItem;
import com.bt.shop.model.Order;
import com.bt.shop.model.OrderItem;
import com.bt.shop.model.Product;
import com.bt.shop.repository.CartItemRepository;
import com.bt.shop.repository.OrderRepository;
import com.bt.shop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public Order createOrder(Long userId, String receiver, String postcode, String address,
                             String detailAddress, String phone) {

        // 장바구니 가져오기
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("장바구니가 비어 있습니다.");
        }

        // 주문 생성
        Order order = new Order();
        order.setUserId(userId);
        order.setReceiver(receiver);
        order.setPostcode(postcode);
        order.setAddress(address);
        order.setDetailAddress(detailAddress);
        order.setPhone(phone);
        order.setStatus("결제대기");

        int totalPrice = 0;

        // 주문 상품 추가
        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("상품 없음: " + cartItem.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(product.getProductId());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setLinePrice(product.getPrice() * cartItem.getQuantity());

            order.getOrderItems().add(orderItem);

            totalPrice += orderItem.getLinePrice();
        }

        order.setTotalPrice(totalPrice);

        // 주문 저장
        orderRepository.save(order);

        // 장바구니 비우기
        cartItemRepository.deleteByUserId(userId);

        return order;
    }
}