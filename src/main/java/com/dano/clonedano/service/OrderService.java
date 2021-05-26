package com.dano.clonedano.service;

import com.dano.clonedano.dto.OrderRequestDto;
import com.dano.clonedano.dto.OrderResponseDto;
import com.dano.clonedano.model.Order;
import com.dano.clonedano.model.Product;
import com.dano.clonedano.model.User;
import com.dano.clonedano.repository.OrderRepository;
import com.dano.clonedano.repository.ProductRepository;
import com.dano.clonedano.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public List<OrderResponseDto> getOrder(UserDetailsImpl userDetails){
        User user = userDetails.getUser();

        List<Order> orderList = orderRepository.findByUserOrderByModifiedAtDesc(user);

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for(Order order : orderList){
            Product product = productRepository.findByProductId(order.getProduct().getProductId());

            String strPrice = product.getPrice().replace("원","").replace(",","");
            int price = Integer.parseInt(strPrice) * order.getAmount();
            strPrice = String.format("%,d",price);

            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .productId(product.getProductId())
                    .title(product.getTitle())
                    .imageUrl(product.getImageUrl())
                    .price(strPrice + "원")
                    .amount(order.getAmount())
                    .build();

            orderResponseDtoList.add(orderResponseDto);
        }
        return orderResponseDtoList;
    }

    public Long registerOrder(UserDetailsImpl userDetails, OrderRequestDto orderRequestDto){

        User user = userDetails.getUser();

        Product product = productRepository.findByProductId(orderRequestDto.getProductId());

        Order order = Order.builder()
                .product(product)
                .user(user)
                .amount(orderRequestDto.getAmount())
                .build();

        orderRepository.save(order);

        return order.getOrderId();
    }

}
