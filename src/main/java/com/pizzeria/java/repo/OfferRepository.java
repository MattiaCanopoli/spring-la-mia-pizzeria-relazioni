package com.pizzeria.java.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pizzeria.java.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Integer> {

}
