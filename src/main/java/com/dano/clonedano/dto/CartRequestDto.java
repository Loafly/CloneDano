package com.dano.clonedano.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartRequestDto {

    private Long productId;

    private int amount;
}
