package com.dano.clonedano.controller;

import com.dano.clonedano.dto.ProductResponseDto;
import com.dano.clonedano.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/product")
    public ProductResponseDto getProduct(){
        productService.getProduct();
        return null;
    }

}
