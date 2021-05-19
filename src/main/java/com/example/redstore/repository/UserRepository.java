package com.example.redstore.repository;

import com.example.redstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);
}
