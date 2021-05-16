package com.dano.clonedano.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDto {

    private Long procductId;

    private String imageUrl;

    private String name;

    private String price;

    private boolean isDano;

    private boolean isFree;

    private boolean isTrending;

    private boolean isBestDeal;

    private boolean isNew;

}
