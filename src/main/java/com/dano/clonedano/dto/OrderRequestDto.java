package com.dano.clonedano.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class OrderRequestDto {

    @NotNull
    private Long productId;

    private int amount;
}
