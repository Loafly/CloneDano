package com.dano.clonedano.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
}
