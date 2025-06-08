package com.bt.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemViewModel {
    private Long productId;

    private int unitPrice;
    private int quantity;
    private int linePrice; // unitPrice * quantity

    private String imageUrl;
    private String productName;
}
