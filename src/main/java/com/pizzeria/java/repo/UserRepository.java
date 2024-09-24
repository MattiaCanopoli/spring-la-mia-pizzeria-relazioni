package com.pizzeria.java.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pizzeria.java.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByUsername(String username);
}
