package com.pizzeria.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pizzeria.java.model.Ingredient;
import com.pizzeria.java.model.Pizza;
import com.pizzeria.java.repo.PizzaRepository;
import com.pizzeria.java.service.IngredientService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

	@Autowired
	private IngredientService iService;

	@Autowired
	private PizzaRepository pRepo;

	@GetMapping
	public String index(Model model) {

		model.addAttribute("ingredients", iService.findActive());
		return "/ingredients/index";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("ingredient", new Ingredient());

		return "ingredients/create";
	}

	// STORE
	@PostMapping("/create")
	public String store(Model model, @Valid @ModelAttribute("ingredient") Ingredient newIngredient,
			BindingResult bindingResults, RedirectAttributes ra) {
		if (bindingResults.hasErrors()) {
			return ("/ingredients/create");
		}
		ra.addFlashAttribute("createMessage", "ingrediente inserito");
		iService.create(newIngredient);

		return ("redirect:/ingredients");
	}

	// DELETE

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {

		ra.addFlashAttribute("deleteMessage", "ingrediente cancellato");

		List<Pizza> pizzas = pRepo.findAll();

		for (Pizza pizza : pizzas) {
			for (Ingredient ingredient : pizza.getIngredients()) {
				if (ingredient.getId().equals(id)) {
					pizza.getIngredients().remove(ingredient);
				}
			}
		}

		iService.disable(id);

		return ("redirect:/ingredients");
	}

}
