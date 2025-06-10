package com.bt.shop.controller;

import com.bt.shop.model.Order;
import com.bt.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DeliveryController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/delivery")
    public String DeliveryPage(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);

        return "delivery_page";
    }
    @PostMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return "redirect:/delivery";
    }
}
