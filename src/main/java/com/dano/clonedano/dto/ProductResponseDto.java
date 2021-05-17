package com.dano.clonedano.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDto {

    private Long procductId;

    private String imageUrl;

    private String title;

    private String price;

    private boolean isDano;

    private boolean isFree;

    private boolean isThrifty;

    private boolean isBest;

    private boolean isNew;

}
