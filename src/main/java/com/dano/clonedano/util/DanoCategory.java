package com.dano.clonedano.util;

import lombok.Getter;

@Getter
public enum DanoCategory {
    allProduct("전체 보기",0),
    bestProduct("인기 상품",147),
    newProdcut("신상품",96),
    danoProdcut("다노제품",25),
    ThriftyProdcut("알뜰 상품",159),
    freeProdcut("무료 배송",80);

    private final String krCategory;
    private final int categoryNumber;

    DanoCategory(String krCategory, int categoryNumber){
        this.krCategory = krCategory;
        this.categoryNumber = categoryNumber;
    }
}
