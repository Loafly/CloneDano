package com.dano.clonedano.repository;

import com.dano.clonedano.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
