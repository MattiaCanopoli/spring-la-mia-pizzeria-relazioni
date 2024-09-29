package com.pizzeria.java.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "offer")
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private LocalDate start;

	@NotNull
	private LocalDate end;

	@NotNull
	@NotEmpty
	@Size(min = 3, max = 35)
	@Column(name = "offer_name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "pizza_id", nullable = false)
	@JsonBackReference
	private Pizza pizza;

	@Transient
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM yy");

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getStart() {
		return start;
	}

	public String getFormattedStart() {
		return start.format(format);
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public String getFormattedEnd() {
		return end.format(format);
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public String getName() {
		return name;
	}

	public void setName(String offerName) {
		this.name = offerName;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

}
