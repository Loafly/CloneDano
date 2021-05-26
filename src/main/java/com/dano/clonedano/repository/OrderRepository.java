package com.dano.clonedano.repository;

import com.dano.clonedano.model.Order;
import com.dano.clonedano.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByModifiedAt(User user);
}
