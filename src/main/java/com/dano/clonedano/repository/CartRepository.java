package com.dano.clonedano.repository;

import com.dano.clonedano.model.Cart;
import com.dano.clonedano.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    Optional<Cart> findByCartId(Long cartId);
}
