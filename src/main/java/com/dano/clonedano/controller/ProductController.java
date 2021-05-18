package com.dano.clonedano.controller;

import com.dano.clonedano.dto.ProductResponseDto;
import com.dano.clonedano.repository.ProductRepository;
import com.dano.clonedano.service.ProductService;
import com.dano.clonedano.util.DanoShopCrawling;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/api/product/{menu}")
    public List<ProductResponseDto> getAllProduct(@PathVariable("menu") String menu){
        return productService.getProduct(menu);
    }

    @GetMapping("/api/crawlingProduct")
    public void crawlingProducts(){
        DanoShopCrawling.DanoShopCrawlingFunc(productRepository);
    }

}
