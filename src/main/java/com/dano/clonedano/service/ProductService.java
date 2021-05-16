package com.dano.clonedano.service;

import com.dano.clonedano.dto.ProductResponseDto;
import com.dano.clonedano.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDto getProduct(){
        return null;
    }
}
