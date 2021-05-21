package com.dano.clonedano.repository;

import com.dano.clonedano.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByTitle(String title);
    Product findByProductId(Long productId);

    @Query("select p from Product p where p.isBest = true")
    List<Product> findByBest();

    @Query("select p from Product p where p.isNew = true")
    List<Product> findByNew();

    @Query("select p from Product p where p.isThrifty = true")
    List<Product> findByThrifty();

    @Query("select p from Product p where p.isFree = true")
    List<Product> findByFree();

    @Query("select p from Product p where p.isDano = true")
    List<Product> findByDano();


}
