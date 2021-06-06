package com.dano.clonedano.service;

import com.dano.clonedano.dto.ProductResponseDto;
import com.dano.clonedano.model.Product;
import com.dano.clonedano.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    static int SIZE = 100;

    @BeforeAll
    void beforeAll(){

    }

    @BeforeEach
    void setUp() {
        String title = "title";
        String price = "price";
        String imageUrl = "imageUrl";
        boolean isDano = false;
        boolean isFree = false;
        boolean isThrifty = false;
        boolean isBest = false;
        boolean isNew = false;

        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < SIZE; i++){
            if (i < 10){
                isDano = true;
            } else if(i < 15){
                isFree = true;
            } else if(i < 22){
                isThrifty = true;
            } else if(i < 30){
                isBest = true;
            } else if(i < 45){
                isNew = true;
            } else if (i < 60){
                isDano = true;
            } else if(i < 73){
                isFree = true;
            } else if(i < 79){
                isThrifty = true;
            } else if(i < 91){
                isBest = true;
            } else if(i < 100){
                isNew = true;
            }

            Product product = Product.builder()
                    .title(title)
                    .price(price)
                    .imageUrl(imageUrl)
                    .isDano(isDano)
                    .isFree(isFree)
                    .isThrifty(isThrifty)
                    .isBest(isBest)
                    .isNew(isNew)
                    .build();

            productList.add(product);
        }

        productRepository.saveAll(productList);
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("모든상품 조회 - 성공")
    void getAllProduct() {
        List<ProductResponseDto> productResponseDtoList = productService.getProduct("all");
        List<Product> productList = productRepository.findAll();

        assertEquals(productResponseDtoList.size(), productList.size());
    }

    @Test
    @DisplayName("다노상품 조회 - 성공")
    void getDanoProduct() {
        List<ProductResponseDto> productResponseDtoList = productService.getProduct("dano");
        List<Product> productList = productRepository.findByDano();

        assertEquals(productResponseDtoList.size(), productList.size());
    }

    @Test
    @DisplayName("무료배송 조회 - 성공")
    void getFreeProduct() {
        List<ProductResponseDto> productResponseDtoList = productService.getProduct("free");
        List<Product> productList = productRepository.findByFree();

        assertEquals(productResponseDtoList.size(), productList.size());
    }

    @Test
    @DisplayName("베스트상품 조회 - 성공")
    void getBestProduct() {
        List<ProductResponseDto> productResponseDtoList = productService.getProduct("best");
        List<Product> productList = productRepository.findByBest();

        assertEquals(productResponseDtoList.size(), productList.size());
    }

    @Test
    @DisplayName("신상품 조회 - 성공")
    void getNewProduct() {
        List<ProductResponseDto> productResponseDtoList = productService.getProduct("new");
        List<Product> productList = productRepository.findByNew();

        assertEquals(productResponseDtoList.size(), productList.size());
    }

    @Test
    @DisplayName("알뜰상품 조회 - 성공")
    void getThriftyProduct() {
        List<ProductResponseDto> productResponseDtoList = productService.getProduct("thrifty");
        List<Product> productList = productRepository.findByThrifty();

        assertEquals(productResponseDtoList.size(), productList.size());
    }
}