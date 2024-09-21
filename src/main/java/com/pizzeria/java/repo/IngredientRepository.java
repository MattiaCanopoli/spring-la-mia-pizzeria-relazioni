package com.pizzeria.java.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pizzeria.java.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

	@Query(value = "SELECT * FROM db_pizzeria.ingredient WHERE ISNULL(deleted_at)", nativeQuery = true)
	public List<Ingredient> findAllNotDeleted();

}
