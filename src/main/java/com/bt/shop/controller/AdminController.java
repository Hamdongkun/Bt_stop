package com.bt.shop.controller;

import com.bt.shop.model.Product;
import com.bt.shop.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;

@Controller
public class AdminController {

    private final ProductRepository productRepository;

    public AdminController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/admin")
    public String showManagerPage() {
        return "manager_page";
    }

    @PostMapping("/admin/product")
    public String saveProduct(
            @RequestParam Long productId,
            @RequestParam Long sellerId,
            @RequestParam Long categoryId,
            @RequestParam String productName,
            @RequestParam String description,
            @RequestParam int price,
            @RequestParam int stock,
            @RequestParam(required = false) MultipartFile imageFile
    ) {
        Product product = productRepository.findById(productId).orElseGet(() -> {
            Product p = new Product();
            p.setProductId(productId);
            return p;
        });

        product.setSellerId(sellerId);
        product.setCategoryId(categoryId);

        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);

        // 이미지 저장 및 리사이징
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String uploadDir = "src/main/resources/static/img/products/";
                String fileName = imageFile.getOriginalFilename();

                // 저장될 경로
                File destFile = new File(uploadDir + fileName);
                destFile.getParentFile().mkdirs();
                imageFile.transferTo(destFile);

                BufferedImage originalImage = ImageIO.read(destFile);
                int targetWidth = 400;
                int targetHeight = (originalImage.getHeight() * targetWidth) / originalImage.getWidth();

                BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = resized.createGraphics();
                g.drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
                g.dispose();

                ImageIO.write(resized, "jpg", destFile);

                // 경로 저장
                product.setImageUrl("/img/products/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productRepository.save(product);
        return "redirect:/main";
    }
}
