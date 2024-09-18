package com.pizzeria.java.controller;

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

import com.pizzeria.java.model.Offer;
import com.pizzeria.java.repo.OfferRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offers")
public class OfferController {

	@Autowired
	private OfferRepository offerRepo;

	@PostMapping("/create")
	public String store(Model model, @Valid @ModelAttribute("offer") Offer offerForm, BindingResult br,
			RedirectAttributes ra) {

		if (br.hasErrors()) {
			return "/offers/create";
		}

		offerRepo.save(offerForm);
		return "redirect:/pizzas/" + offerForm.getPizza().getId();

	}

	@GetMapping
	public String index(Model model) {
		model.addAttribute("offers", offerRepo.findAll());
		return "/offers/index";
	}

	// DELETE

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes deleteMessage) {

		offerRepo.deleteById(id);

		return ("redirect:/offers");
	}

}
