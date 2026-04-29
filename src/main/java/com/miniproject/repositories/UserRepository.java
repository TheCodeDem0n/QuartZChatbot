package com.miniproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
