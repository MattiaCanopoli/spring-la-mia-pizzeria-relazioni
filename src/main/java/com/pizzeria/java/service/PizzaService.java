package com.pizzeria.java.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pizzeria.java.model.Pizza;
import com.pizzeria.java.repo.PizzaRepository;

@Service
public class PizzaService {

	@Autowired
	PizzaRepository pizzaRepo;

	public Pizza getById(Integer id) {
		return pizzaRepo.findById(id).get();

	}

	public Optional<Pizza> findById(Integer id) {
		return pizzaRepo.findById(id);

	}

	public List<Pizza> findAll() {
		return pizzaRepo.findAll(Sort.by("name"));

	}

	public List<Pizza> findByName(String name) {
		return pizzaRepo.findByNameContains(name);
	}

	public Pizza save(Pizza pizza) {
		return pizzaRepo.save(pizza);
	}

	public void delete(Integer id) {
		pizzaRepo.deleteById(id);
	}
}
