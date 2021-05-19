package com.dano.clonedano.controller;

import com.dano.clonedano.dto.OrderRequestDto;
import com.dano.clonedano.dto.OrderResponseDto;
import com.dano.clonedano.security.UserDetailsImpl;
import com.dano.clonedano.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/api/order")
    public List<OrderResponseDto> getOrder(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return orderService.getOrder(userDetails);
    }

    @PostMapping("/api/order")
    public Long registerOrder(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody OrderRequestDto orderRequestDto){
        return orderService.registerOrder(userDetails, orderRequestDto);
    }

}
