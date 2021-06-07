package com.dano.clonedano.service;

import com.dano.clonedano.dto.*;
import com.dano.clonedano.model.Cart;
import com.dano.clonedano.model.Product;
import com.dano.clonedano.repository.CartRepository;
import com.dano.clonedano.repository.OrderRepository;
import com.dano.clonedano.repository.ProductRepository;
import com.dano.clonedano.repository.UserRepository;
import com.dano.clonedano.security.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class CartServiceTest {
    private static final int SIZE = 20;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    private final UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto();
    private final UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
    private String token = null;

    @BeforeEach
    public void setUp(){
        //상품 등록
        String title = "title";
        String price = "50,000원";
        String imageUrl = "imageUrl";
        boolean isDano = false;
        boolean isFree = false;
        boolean isThrifty = false;
        boolean isBest = false;
        boolean isNew = false;

        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < SIZE; i++){
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

        //회원 등록
        userSignUpRequestDto.setUserName("userName");
        userSignUpRequestDto.setPassword("1234");
        userSignUpRequestDto.setNickName("nickName");
        userSignUpRequestDto.setPhone("010-1234-5678");
        userSignUpRequestDto.setEmail("email@naver.com");

        userService.userSignUp(userSignUpRequestDto);

        userLoginRequestDto.setUserName(userSignUpRequestDto.getUserName());
        userLoginRequestDto.setPassword(userSignUpRequestDto.getPassword());

        token = userService.createToken(userLoginRequestDto);
    }

    @AfterEach
    public void tearDown(){
        cartRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getCarts() {
        //given
        CartRequestDto cartRequestDto = new CartRequestDto();
        List<Product> productList = productRepository.findAll();
        for (int i = 0; i < SIZE; i++){
            cartRequestDto.setProductId(productList.get(i).getProductId());
            cartRequestDto.setAmount(i);
            cartService.registerCart(jwtTokenProvider.getAuthenticationUserDetailsImpl(token), cartRequestDto);
        }

        //when
        List<CartResponseDto> cartResponseDtoList = cartService.getCarts(jwtTokenProvider.getAuthenticationUserDetailsImpl(token));

        //then
        assertEquals(productList.size(), cartResponseDtoList.size());
    }

    @Test
    void registerCart() {
        //given
        CartRequestDto cartRequestDto = new CartRequestDto();
        List<Product> productList = productRepository.findAll();
        cartRequestDto.setProductId(productList.get(0).getProductId());
        cartRequestDto.setAmount(3);

        //when
        Long cartId = cartService.registerCart(jwtTokenProvider.getAuthenticationUserDetailsImpl(token), cartRequestDto);
        Long productId = cartRepository.findByCartId(cartId).get().getProduct().getProductId();

        //then
        assertEquals(productId, productList.get(0).getProductId());
    }

    @Test
    void registerOrderRemoveCart() {
        //given
        CartRequestDto cartRequestDto = new CartRequestDto();
        List<Product> productList = productRepository.findAll();
        for (int i = 0; i < SIZE; i++){
            cartRequestDto.setProductId(productList.get(i).getProductId());
            cartRequestDto.setAmount(i);
            cartService.registerCart(jwtTokenProvider.getAuthenticationUserDetailsImpl(token), cartRequestDto);
        }

        //when
        List<Long> orderIdList = cartService.registerOrderRemoveCart(jwtTokenProvider.getAuthenticationUserDetailsImpl(token));
        List<Cart> cartList = cartRepository.findAll();

        //then
        assertEquals(productList.size(), orderIdList.size());
        assertEquals(0, cartList.size());
    }

    @Test
    void removeCart() {
        //given
        CartRequestDto cartRequestDto = new CartRequestDto();
        List<Product> productList = productRepository.findAll();
        cartRequestDto.setProductId(productList.get(0).getProductId());
        cartRequestDto.setAmount(3);

        //when
        Long cartId = cartService.registerCart(jwtTokenProvider.getAuthenticationUserDetailsImpl(token), cartRequestDto);
        cartService.removeCart(jwtTokenProvider.getAuthenticationUserDetailsImpl(token), cartId);
        List<Cart> cartList = cartRepository.findAll();

        //then
        assertEquals(0, cartList.size());
    }
}