package com.bt.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private Long sellerId;
    private Long categoryId;

    private String productName;
    private String description;
    private String imageUrl; // 예: /img/product/iphone14.jpg

    private int price;
    private int stock;

}
