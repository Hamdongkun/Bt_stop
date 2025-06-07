package com.bt.shop.controller;

import com.bt.shop.service.ProductService;
import com.bt.shop.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MainController {

    private final ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(Model model) {
        List<Product> bestProducts = productService.getTop4BestProducts();
        model.addAttribute("bestProducts", bestProducts);
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
