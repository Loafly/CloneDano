package com.dano.clonedano.service;

import com.dano.clonedano.dto.ProductResponseDto;
import com.dano.clonedano.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponseDto> getAllProduct(){

        return productRepository.findAll().stream().map(
                product -> new ProductResponseDto(
                        product.getProductId(),
                        product.getImageUrl(),
                        product.getTitle(),
                        product.getPrice(),
                        product.isDano(),
                        product.isFree(),
                        product.isThrifty(),
                        product.isBest(),
                        product.isNew()
                )).collect(Collectors.toList());
    }

    public List<ProductResponseDto> getDanoProduct(){
        return productRepository.findByDano().stream().map(
                product -> new ProductResponseDto(
                        product.getProductId(),
                        product.getImageUrl(),
                        product.getTitle(),
                        product.getPrice(),
                        product.isDano(),
                        product.isFree(),
                        product.isThrifty(),
                        product.isBest(),
                        product.isNew()
                )).collect(Collectors.toList());
    }

    public List<ProductResponseDto> getFreeProduct(){
        return productRepository.findByFree().stream().map(
                product -> new ProductResponseDto(
                        product.getProductId(),
                        product.getImageUrl(),
                        product.getTitle(),
                        product.getPrice(),
                        product.isDano(),
                        product.isFree(),
                        product.isThrifty(),
                        product.isBest(),
                        product.isNew()
                )).collect(Collectors.toList());
    }

    public List<ProductResponseDto> getBestProduct(){
        return productRepository.findByBest().stream().map(
                product -> new ProductResponseDto(
                        product.getProductId(),
                        product.getImageUrl(),
                        product.getTitle(),
                        product.getPrice(),
                        product.isDano(),
                        product.isFree(),
                        product.isThrifty(),
                        product.isBest(),
                        product.isNew()
                )).collect(Collectors.toList());
    }

    public List<ProductResponseDto> getNewProduct(){
        return productRepository.findByNew().stream().map(
                product -> new ProductResponseDto(
                        product.getProductId(),
                        product.getImageUrl(),
                        product.getTitle(),
                        product.getPrice(),
                        product.isDano(),
                        product.isFree(),
                        product.isThrifty(),
                        product.isBest(),
                        product.isNew()
                )).collect(Collectors.toList());
    }

    public List<ProductResponseDto> getThriftyProduct(){
        return productRepository.findByThrifty().stream().map(
                product -> new ProductResponseDto(
                        product.getProductId(),
                        product.getImageUrl(),
                        product.getTitle(),
                        product.getPrice(),
                        product.isDano(),
                        product.isFree(),
                        product.isThrifty(),
                        product.isBest(),
                        product.isNew()
                )).collect(Collectors.toList());
    }
}
