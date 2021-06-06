package com.dano.clonedano.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    @NotNull
    private Long productId;

    private int amount;
}
