package com.pizzeria.java.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pizzeria.java.model.Pizza;
import com.pizzeria.java.service.PizzaService;

@RestController
@CrossOrigin
@RequestMapping("/api/pizzas")
public class PizzaRestController {

	@Autowired
	PizzaService pService;

	@GetMapping
	public List<Pizza> index(@RequestParam(name = "name", required = false) String name) {

		List<Pizza> response;

		if (name != null && !name.isEmpty())
			response = pService.findByName(name);
		else {
			response = pService.findAll();
		}
		return response;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pizza> show(@PathVariable("id") Integer id) {

		Optional<Pizza> pizza = pService.findById(id);

		if (pizza.isPresent()) {
			return new ResponseEntity<>(pizza.get(), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PostMapping
	public Pizza store(@RequestBody Pizza pizza) {

		Pizza newPizza = pService.save(pizza);
		return newPizza;

	}

	@PutMapping("/{id}")
	public ResponseEntity<Pizza> update(@PathVariable("id") Integer id, @RequestBody Pizza pizza) {

		Optional<Pizza> foundPizza = pService.findById(id);

		if (foundPizza.isPresent()) {
			pizza = pService.update(pizza, id);
			return new ResponseEntity<>(pizza, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
		Optional<Pizza> foundPizza = pService.findById(id);

		if (foundPizza.isPresent()) {
			pService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

}
