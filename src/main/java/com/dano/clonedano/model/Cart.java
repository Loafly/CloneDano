package com.dano.clonedano.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "carts")
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(nullable = false)
    private String amount;
}
