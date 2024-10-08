package com.pizzeria.java.model;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pizza")
public class Pizza {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	@NotNull
	@Column(nullable = false)
	private String name;

	@Column(name = "photo_url")
	private String photoUrl;

	@NotNull
	@Column(nullable = false)
	private Float price;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	private String description;

	@Transient
	@JsonIgnore
	private DecimalFormat format = new DecimalFormat("#,##0.00");

	@Transient
	@JsonIgnore
	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm");

	@OneToMany(mappedBy = "pizza", cascade = { CascadeType.REMOVE })
	private List<Offer> offers;

	@ManyToMany
	@JoinTable(name = "ingredients_pizzas", joinColumns = @JoinColumn(name = "pizza_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
	private Set<Ingredient> ingredients;

	public DecimalFormat getFormat() {
		return format;
	}

	public void setFormat(DecimalFormat format) {
		this.format = format;
	}

	public DateTimeFormatter getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateTimeFormatter dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photo_url) {
		this.photoUrl = photo_url;
	}

	public Float getPrice() {
		return this.price;
	}

	@JsonIgnore
	public String getFormattedPrice() {
		if (this.price != null) {
			return format.format(this.price);
		}

		return this.price.toString();
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	@JsonIgnore
	public String getFormattedUpdatedAt() {
		if (this.updatedAt != null) {
			return this.updatedAt.toLocalDateTime().format(dateFormat);
		}

		return null;
	}

	public String getDescription() {
		return this.description;
	}

	@JsonIgnore
	public String getFormattedDescription() {

		if (this.description != null && !this.description.isEmpty()) {
			String firstLetter = this.description.substring(0, 1).toUpperCase();
			String restOfTheString = this.description.substring(1);

			description = firstLetter + restOfTheString;
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public String capName() {
		String firstLetter = this.name.substring(0, 1).toUpperCase();
		String restOfTheString = this.name.substring(1);

		return firstLetter + restOfTheString;
	}

}
