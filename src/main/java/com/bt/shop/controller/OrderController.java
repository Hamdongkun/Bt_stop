package com.bt.shop.controller;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    @PostMapping("/order/submit")
    public String submitOrder(@RequestParam String receiverName,
                              @RequestParam String postcode,
                              @RequestParam String address,
                              @RequestParam String detailAddress,
                              @RequestParam String phoneNumber,
                              Model model) {

        // 1️⃣ 주문정보 DB에 저장 (주문 상태 = 결제대기)
        // 예시용 로그만 출력
        System.out.println("주문 정보 저장됨:");
        System.out.println(receiverName + ", " + postcode + ", " + address + ", " + detailAddress + ", " + phoneNumber);

        // 2️⃣ PG사 결제 준비 API 호출
        // 예시: PG로 redirect URL 구성 → 여기선 임시로 성공 페이지로 이동
        // 실제로는 PG 결제 URL 받아서 redirect 시킴 (ex: 카카오페이 URL)

        // ★ 예시용으로 임시 성공 페이지로 이동 처리
        return "redirect:/order/success";
    }

    // 주문 성공 페이지 GET
    @GetMapping("/order/success")
    public String orderSuccess() {
        return "order_success"; // templates/order_success.html 필요
    }
}