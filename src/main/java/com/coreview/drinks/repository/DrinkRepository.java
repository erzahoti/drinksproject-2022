package com.coreview.drinks.repository;

import com.coreview.drinks.data.drink.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrinkRepository extends JpaRepository<Drink, Integer> {

    @Override
    List<Drink> findAll();
}
