package com.dano.clonedano.service;

import com.dano.clonedano.dto.*;
import com.dano.clonedano.model.Product;
import com.dano.clonedano.repository.OrderRepository;
import com.dano.clonedano.repository.ProductRepository;
import com.dano.clonedano.repository.UserRepository;
import com.dano.clonedano.security.JwtTokenProvider;
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
class OrderServiceTest {

    private static final int SIZE = 20;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private final UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto();
    private final UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
    private String token = null;

    @BeforeEach
    public void setUp(){
        //상품 등록
        String title = "title";
        String price = "50000";
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
        orderRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getOrder() {

        //given
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        List<Product> productList = productRepository.findAll();
        for (int i = 0; i < SIZE; i++){
            orderRequestDto.setProductId(productList.get(i).getProductId());
            orderRequestDto.setAmount(i);
            orderService.registerOrder(jwtTokenProvider.getAuthenticationUserDetailsImpl(token), orderRequestDto);
        }

        //when
        List<OrderResponseDto> orderResponseDtoList = orderService.getOrder(jwtTokenProvider.getAuthenticationUserDetailsImpl(token));

        //then
        assertEquals(productList.size(), orderResponseDtoList.size());

    }

    @Test
    void registerOrder() {
        //given
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        List<Product> productList = productRepository.findAll();
        orderRequestDto.setProductId(productList.get(0).getProductId());
        orderRequestDto.setAmount(3);

        //when
        Long productId = orderService.registerOrder(jwtTokenProvider.getAuthenticationUserDetailsImpl(token), orderRequestDto);

        //then
        assertEquals(productId, productList.get(0).getProductId());
    }
}