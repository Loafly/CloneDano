package com.dano.clonedano.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderResponseDto {

    private Long productId;

    private String imageUrl;

    private String title;

    private String price;

    private int amount;
}
