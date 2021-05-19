package com.dano.clonedano.controller;

import com.dano.clonedano.dto.CartRequestDto;
import com.dano.clonedano.dto.CartResponseDto;
import com.dano.clonedano.model.Cart;
import com.dano.clonedano.security.UserDetailsImpl;
import com.dano.clonedano.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/api/cart")
    public List<CartResponseDto> getCart(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return cartService.getCarts(userDetails);
    }

    @PostMapping("/api/cart")
    public Long registerCart(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CartRequestDto cartRequestDto){
        return cartService.registerCart(userDetails, cartRequestDto);
    }

    @DeleteMapping("/api/cart/{cartId}")
    public void removeCart(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long cartId){
        cartService.removeCart(userDetails, cartId);
    }
}
