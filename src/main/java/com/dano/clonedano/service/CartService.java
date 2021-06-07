package com.dano.clonedano.service;

import com.dano.clonedano.dto.CartRequestDto;
import com.dano.clonedano.dto.CartResponseDto;
import com.dano.clonedano.model.Cart;
import com.dano.clonedano.model.Order;
import com.dano.clonedano.model.Product;
import com.dano.clonedano.model.User;
import com.dano.clonedano.repository.CartRepository;
import com.dano.clonedano.repository.OrderRepository;
import com.dano.clonedano.repository.ProductRepository;
import com.dano.clonedano.repository.UserRepository;
import com.dano.clonedano.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public List<CartResponseDto> getCarts(UserDetailsImpl userDetails){
        User user = userDetails.getUser();

        List<Cart> cartList = cartRepository.findByUser(user);

        List<CartResponseDto> cartResponseDtoList = new ArrayList<>();

        for (Cart cart : cartList){
            Product product = productRepository.findByProductId(cart.getProduct().getProductId());

            String strPrice = product.getPrice().replace("원","").replace(",","");
            int price = Integer.parseInt(strPrice) * cart.getAmount();
            strPrice = String.format("%,d",price);

            CartResponseDto cartResponseDto = CartResponseDto.builder()
                    .cartId(cart.getCartId())
                    .amount(cart.getAmount())
                    .title(product.getTitle())
                    .imageUrl(product.getImageUrl())
                    .price(strPrice + "원")
                    .build();
            cartResponseDtoList.add(cartResponseDto);
        }

        return cartResponseDtoList;
    }

    public Long registerCart(UserDetailsImpl userDetails, CartRequestDto cartRequestDto){
        Product product = productRepository.findById(cartRequestDto.getProductId()).orElse(null);
        User user = userDetails.getUser();

        Cart cart = Cart.builder()
                .user(user)
                .product(product)
                .amount(cartRequestDto.getAmount())
                .build();

        cartRepository.save(cart);

        return cart.getCartId();
    }

    public List<Long> registerOrderRemoveCart(UserDetailsImpl userDetails){

        User user = userDetails.getUser();

        List<Cart> cartList = cartRepository.findByUser(user);
        List<Long> longList = new ArrayList<>();

        for(Cart cart : cartList){
            Order order = Order.builder()
                    .user(user)
                    .product(cart.getProduct())
                    .amount(cart.getAmount())
                    .build();

            orderRepository.save(order);
            longList.add(order.getOrderId());
            cartRepository.delete(cart);
        }

        return longList;
    }

    public void removeCart(UserDetailsImpl userDetails, Long cartId){
        User currentUser = userDetails.getUser();

        Cart cart = cartRepository.findByCartId(cartId).orElse(null);
        assert cart != null;

        User cartUser = userRepository.findById(cart.getUser().getUserId()).orElse(null);

        assert cartUser != null;
        if (cartUser.equals(currentUser)){
            cartRepository.delete(cart);
        }
    }
}
