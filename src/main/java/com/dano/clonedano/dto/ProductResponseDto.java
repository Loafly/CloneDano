package com.dano.clonedano.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDto {

    private Long productId;

    private String imageUrl;

    private String title;

    private String price;
}
