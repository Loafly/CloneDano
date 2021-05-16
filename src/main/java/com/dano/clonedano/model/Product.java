package com.dano.clonedano.model;

import lombok.*;

import javax.persistence.*;


@Data
@Entity
@Table(name = "products")
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String price;

    private String imageUrl;

    private boolean isDano;

    private boolean isFree;

    private boolean isThrifty;

    private boolean isBest;

    private boolean isNew;
}
