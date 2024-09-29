package com.pizzeria.java.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pizzeria.java.model.Offer;
import com.pizzeria.java.model.Pizza;
import com.pizzeria.java.service.IngredientService;
import com.pizzeria.java.service.PizzaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

	@Autowired
	private PizzaService pService;

	@Autowired
	private IngredientService iService;

	// READ
	@GetMapping
	public String index(Model model, @RequestParam(name = "name", required = false) String name,
			Authentication authentication) {

		List<Pizza> pizzas;

		if (name != null && !name.isEmpty()) {

			pizzas = pService.findByName(name);
		} else {
			pizzas = pService.findAll();
		}
		model.addAttribute("pizzas", pizzas);
		model.addAttribute("username", authentication.getName());
		return "/pizzas/index";
	}

	// SHOW
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("thisPizza", pService.getById(id));
		model.addAttribute("ingredients", iService.findActive());
		// model.addAttribute("pizzas", pizzaRepo.findAll());
		return "/pizzas/show";
	}

	// CREATE
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("ingredients", iService.findActive());
		return ("/pizzas/create");
	}

	// STORE
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("pizza") Pizza pizzaForm, BindingResult bindingResults, Model model,
			RedirectAttributes redirectMessage) {
		model.addAttribute("ingredients", iService.findActive());
		if (bindingResults.hasErrors()) {
			return ("/pizzas/create");
		}

		pService.save(pizzaForm);

		redirectMessage.addFlashAttribute("createMessage", pizzaForm.capName() + " è stata aggiunta");
		return ("redirect:/pizzas");
	}

	// EDIT
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("pizza", pService.getById(id));
		model.addAttribute("ingredients", iService.findActive());
		return ("/pizzas/edit");
	}
	// UPDATE

	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("pizza") Pizza pizzaEdit, BindingResult bindingResults, Model model) {
		if (bindingResults.hasErrors()) {
			model.addAttribute("ingredients", iService.findActive());
			return ("/pizzas/edit");
		}

		pService.save(pizzaEdit);
		return ("redirect:/pizzas/{id}");
	}
	// DELETE

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes deleteMessage) {

		String pizzaName = pService.getById(id).capName();
		pService.delete(id);

		deleteMessage.addFlashAttribute("deleteMessage", pizzaName + " è stata rimossa");
		return ("redirect:/pizzas");
	}

	// OFFER
	@GetMapping("/{id}/offer")
	public String offerCreate(@PathVariable("id") Integer id, Model model) {

		Offer offer = new Offer();
		offer.setPizza(pService.getById(id));
		offer.setStart(LocalDate.now());
		model.addAttribute("offer", offer);
		return "/offers/create";
	}
}
