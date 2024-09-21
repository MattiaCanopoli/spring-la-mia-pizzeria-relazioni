package com.pizzeria.java.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizzeria.java.model.Ingredient;
import com.pizzeria.java.repo.IngredientRepository;

@Service
public class IngredientService {

	@Autowired
	private IngredientRepository iRepo;

	public List<Ingredient> findAll() {
		List<Ingredient> ingredients = iRepo.findAll();
		return ingredients;
	}

	public List<Ingredient> findActive() {
		List<Ingredient> ingredients = iRepo.findAllNotDeleted();
		return ingredients;
	}

	public Ingredient findById(Integer id) {
		Ingredient ing = iRepo.findById(id).get();
		return ing;
	}

	public void create(Ingredient ingredient) {
		ingredient.setCreatedAt(LocalDate.now());
		iRepo.save(ingredient);
	}

	public void delete(Integer id) {
		iRepo.deleteById(id);
	}

	public void disable(Integer id) {
		Ingredient ingredientToDelete = iRepo.findById(id).get();
		ingredientToDelete.setDeletedAt(LocalDate.now());
		iRepo.save(ingredientToDelete);
	}

}
