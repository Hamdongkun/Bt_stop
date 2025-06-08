package com.bt.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeliveryController {
    @GetMapping("/delivery")
    public String DeliveryPage() {
        // 필요하면 model 에 데이터 담기 (ex: 배송정보, 주문정보 등)

        return "delivery_page";
    }
}
