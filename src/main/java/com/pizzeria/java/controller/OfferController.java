package com.pizzeria.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pizzeria.java.model.Offer;
import com.pizzeria.java.repo.OfferRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offers")
public class OfferController {

	private OfferRepository offerRepo;

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("offer", new Offer());
		return "";
	}

	@PostMapping("/create")
	public String store(Model model, @Valid @ModelAttribute("offer") Offer offerForm, BindingResult br,
			RedirectAttributes ra) {

		if (br.hasErrors()) {
			return "/create";
		}

		offerRepo.save(offerForm);
		return "../pizzas/index";

	}

}
