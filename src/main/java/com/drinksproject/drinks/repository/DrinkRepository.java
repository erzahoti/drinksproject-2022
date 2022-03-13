package com.drinksproject.drinks.repository;

import com.drinksproject.drinks.data.drink.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DrinkRepository extends JpaRepository<Drink, UUID> {

    @Override
    List<Drink> findAll();
}
