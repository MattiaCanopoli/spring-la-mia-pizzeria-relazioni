package com.pizzeria.java.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pizzeria.java.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

	public List<Pizza> findByNameContains(String name);

}
