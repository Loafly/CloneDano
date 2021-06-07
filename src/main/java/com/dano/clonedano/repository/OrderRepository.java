package com.dano.clonedano.repository;

import com.dano.clonedano.model.Order;
import com.dano.clonedano.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByModifiedAtDesc(User user);
    Optional<Order> findByOrderId(Long orderId);
}
