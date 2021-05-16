package com.dano.clonedano.repository;

import com.dano.clonedano.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
}
